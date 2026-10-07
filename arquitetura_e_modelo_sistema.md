# Especificação Técnica e Arquitetura do Sistema
## Motor de Jogo Baseado em Grafos e Estruturas de Dados Avançadas

Este documento descreve detalhadamente a arquitetura, organização de pacotes e responsabilidades das classes que compõem o sistema do jogo. O motor foi desenhado utilizando conceitos avançados de Estruturas de Dados (ED), como grafos ponderados, pilhas e listas duplamente ligadas customizadas, garantindo independência de coleções nativas do Java.

---

## 🗺️ Tema 1 — Modelo de Dados

O modelo de dados define a fundação estrutural do jogo, mapeando o mundo (grafo), os jogadores e as mecânicas de eventos/punições.

### 📌 Mapa (`model/map/`)

| Classe | Descrição | Relevância no Sistema |
| :--- | :--- | :--- |
| `Location.java` | Vértice imutável do grafo. Define o `id`, `nome`, papel da sala (`SPAWN`, `DUNGEON`, `ENDGAME`, `LEVER_ROOM`), nível de perigo (0 a 5), cor e bioma para renderização. | **Crítico:** É o alicerce de toda a navegação e posicionamento no jogo. |
| `GameMap.java` | Grafo ponderado de `Location` construído sobre a estrutura `Network` da `API_ED`. Expõe algoritmos como Dijkstra (`shortestPath`, `shortestPathCost`), vizinhos e conectividade. | É o mapa em memória utilizado ativamente pelo motor do jogo durante toda a sessão. |
| `ShortcutEdge.java` | Aresta de atalho que conecta salas de nível 2 a salas de nível 4. Pode ter um evento associado (enigma ou alavanca). | Apenas 50% dos atalhos são visíveis ao jogador; a outra metade exige descoberta. |

### 👤 Jogador (`model/player/`)

* **`Player.java`**: Representa um jogador (humano ou bot).
    * **Atributos**: HP (Pontos de Vida), localização atual, sala de spawn, inventário e turnos bónus.
    * **Históricos de Movimentação**:
        * `movementHistory` (via `LinkedStack`): Permite a operação de *backtrack* (retroceder) em complexidade tempo $O(1)$.
        * `fullPathHistory` (via `DoublyLinkedList`): Histórico completo e persistente para o relatório final (nunca é apagado).
    * **Importância**: É a chave de toda a interação com o motor principal (`GameEngine`).

### 🎭 Eventos (`model/event/`)

* **`Event.java`**: Classe base abstrata para todos os eventos do mapa.
* **`EventType.java`**: Enumeração com os tipos de eventos possíveis (`ENIGMA`, `LEVER`), definindo respetivos pesos e limiares de perigo para seleção ponderada.
* **`Enigma.java`**: Evento do tipo pergunta-resposta com nível de dificuldade associado. Armazena a pergunta, a resposta correta e um ID único. Crucial para o sistema de progressão.
* **`LeverEvent.java`**: Evento de sala de alavanca. Contém uma lista de objetos `Door` gerada dinamicamente para a sala. O jogador deve escolher uma das portas para avançar.
* **`Door.java`**: Porta individual pertencente a um `LeverEvent`. Possui um destino, uma flag indicando se é a porta "correta" e uma punição associada caso seja errada.

### ⚠️ Punições (`model/punishment/`)

Quando um jogador falha num evento (enigma incorreto ou porta errada numa sala de alavanca), é aplicada uma punição gerada dinamicamente.

```
                  [Punishment (Abstract)]
                             |
       +---------------------+---------------------+
       |                     |                     |
[DamagePunishment]   [KidnapPunishment]   [PushbackPunishment]
```

* **`Punishment.java`**: Abstração base de uma punição, contendo um nível de perigo associado.
* **`PunishmentType.java`**: Enum com os tipos (`DAMAGE`, `KIDNAP`, `PUSHBACK`) e pesos ponderados para seleção aleatória de acordo com a sala.
* **`DamagePunishment.java`**: Aplica dano direto ao HP do jogador.
* **`KidnapPunishment.java`**: Aplica um teleporte forçado do jogador para uma localização aleatória do mapa.
* **`KidnapTarget.java`**: Enumeração que define os alvos possíveis de um rapto/teleporte.
* **`PushbackPunishment.java`**: Força o jogador a recuar $N$ passos no seu histórico de movimentos (`movementHistory`).

---

## ⚙️ Tema 2 — Engine Principal (`engine/core/`)

O núcleo lógico do jogo centraliza as regras de progressão, combate, estados e transições de turno.

* **`GameEngine.java`**: O componente mais central de toda a aplicação. Controla o fluxo de execução e gere o pipeline estrito de **9 passos do método `moveTo()`**:
    1. Verificação de adjacência entre a sala atual e o destino.
    2. Processamento de atalhos e validação de visibilidade/eventos associados.
    3. Ativação de salas de alavanca (`LeverRoom`), se aplicável.
    4. Ativação e resolução de enigmas pendentes na sala.
    5. Efetivação do movimento do jogador (atualização de nós e históricos).
    6. Verificação e resolução de combate PvP (Player vs Player) se dois jogadores ocuparem a mesma coordenada.
    7. Aplicação de consequências pós-combate ou pós-evento.
    8. Verificação das condições de vitória (`ENDGAME`).
    9. Delegação de sub-estados para handlers especializados (morte/respawn ao `RespawnHandler`, rapto ao `KidnapResolver` e IA ao `BotTurnHandler`).

* **`GameDifficulty.java`**: Enumeração que configura os 4 níveis de dificuldade do jogo (`EASY`, `NORMAL`, `HARD`, `HARDCORE`). Controla:
    * Multiplicador de dano recebido.
    * Número máximo de respawns permitidos.
    * Preservação ou perda do inventário após a morte.
    * Sala de regresso obrigatória após falecimento.

* **`MoveResult.java`**: DTO (Data Transfer Object) imutável devolvido pelo método `moveTo()`. Codifica o resultado completo da ação (sucesso, bloqueio, combate, enigma, respawn, sala de alavanca, rapto). A Interface de Utilizador (UI) reage exclusivamente a este objeto, mantendo o encapsulamento do motor.

* **`LocationState.java`**: Armazena o estado mutável e contextual de cada par `(Jogador, Location)`. Guarda se a sala já foi visitada pelo jogador específico, se o enigma local já foi resolvido e o tipo de evento atribuído em runtime. Este estado é limpo no *respawn* para dar uma nova oportunidade de exploração ao jogador.

---

## 🤖 Tema 3 — Bot (`engine/bot/` e `engine/bot/strategy/`)

O jogo suporta inteligências artificiais com diferentes comportamentos e capacidades de navegação e resolução de problemas.

### 🧠 Interface e Estratégias

* **`BotStrategy.java`**: Interface que define o contrato básico para as estratégias de IA através de dois métodos principais: `chooseNextLocation()` e `solveEnigma()`.
* **`RandomBot.java` (Fácil)**: Movimenta-se de forma completamente aleatória entre os vizinhos disponíveis da sala atual. Possui uma taxa fixa de 50% de probabilidade de acertar nos enigmas.
* **`HybridBot.java` (Médio)**: Utiliza o algoritmo de Dijkstra para encontrar o caminho ótimo em 70% das jogadas e move-se aleatoriamente nos restantes 30%. Resolve enigmas com 70% de eficácia.
* **`DijkstraBot.java` (Difícil)**: Omnisciente. Utiliza rigorosamente o algoritmo de Dijkstra para calcular e seguir a rota perfeita até ao objetivo. Responde sempre corretamente a todos os enigmas.

### ⚙️ Handlers de Bot

* **`BotTurnHandler.java`**: Orquestra o turno completo de um Bot. Invoca a estratégia selecionada para escolher o movimento, resolve as interações com salas de alavanca, atalhos e submete as respostas aos enigmas. Foi extraído da classe `GameEngine` para garantir a separação de responsabilidades (SRP).
* **`KidnapResolver.java`**: Resolve as punições de teleporte aleatório e os recuos de posição (`moveBackSteps`). É consumido tanto pelo motor principal quanto pelo turno dos bots.
* **`RespawnHandler.java`**: Controla os contadores individuais de vidas/respawn. Verifica a elegibilidade de renascimento com base na dificuldade do jogo, limpa o progresso das salas (`LocationState`) em caso de fatalidade e invoca o método `Player#respawn()`.

---

## 🎲 Tema 4 — Eventos e Seleção (`engine/event/`)

Módulo responsável pela distribuição dinâmica, aleatoriedade ponderada e validação de mecânicas de jogo.

* **`EnigmaPool.java`**: Gere o banco de enigmas segmentado por nível de dificuldade, implementando **isolamento por jogador**: garante que dois jogadores nunca respondam ao mesmo enigma na mesma sala durante a mesma sessão. Filtra a lista global carregada em memória para disponibilizar apenas os eventos do tipo `ENIGMA`.
* **`EnigmaResolver.java`**: Valida a resposta submetida pelo jogador delegando a lógica para `Enigma#isCorrect()`. Retorna um `EnigmaResult` contendo o ID, flag de sucesso e a resposta processada.
* **`EventSelector.java`**: Aplica uma seleção ponderada (com base nos pesos de `EventType`) para atribuir dinamicamente um tipo de evento a uma sala do tipo `DUNGEON`. O resultado gerado é colocado em cache no `LocationState`.
* **`PunishmentSelector.java`**: Seleciona aleatoriamente o tipo de punição a aplicar recorrendo aos pesos definidos no enum `PunishmentType`.
* **`EventResult.java`**: DTO contendo o desfecho de um evento, detalhando se o jogador foi bem-sucedido ou se falhou, especificando a instância de `Punishment` a aplicar em caso de falha.

---

## 💾 Tema 5 — I/O (`io/`)

Camada responsável pela persistência de dados, carregamento de configurações e geração procedimental de elementos do mapa.

* **`MapLoader.java`**: Lê ficheiros em formato JSON para instanciar e popular um `GameMap`. Durante o processo, invoca o método `generateShortcuts()` para criar de forma procedimental as arestas de atalho entre as salas de nível 2 e 4, definindo também a visibilidade inicial de cada atalho de forma aleatória.
* **`MapSaver.java`**: Serializa o estado atual do `GameMap` de volta para um ficheiro estruturado em JSON.
* **`EnigmaLoader.java`**: Carrega o repositório central de perguntas (`enigmas.json`) e instancia uma lista abstrata do tipo `ListADT<Event>`, utilizada subsequentemente para alimentar o `EnigmaPool`.

---

## 🖥️ Tema 6 — UI (`ui/`)

Interface gráfica desenvolvida em JavaFX, estruturada sob o padrão MVC (Model-View-Controller) e dividida em três fases principais de fluxo de utilizador.

### 🛠️ 1. Setup (Configuração Inicial)

* **`MainApp.java`**: Ponto de entrada (*Entry Point*) da aplicação JavaFX. Carrega a janela inicial de setup.
* **`GameSetupController.java`**: Controlador do ecrã de configuração. Permite selecionar o mapa, o tema dos enigmas, a dificuldade global da sessão e adicionar/configurar os jogadores. Valida os dados de entrada e inicializa o `GameEngine`.
* **`GameStartValidator.java`**: Componente de validação de regras de negócio para o arranque (ex: número mínimo de jogadores ativos, existência de salas de `SPAWN` válidas).
* **`PlayerConfigRow.java`**: Componente visual customizado que representa uma linha de configuração de jogador (definição de nome, tipo: Humano/Bot, e seleção de spawn).
* **`SetupComboPopulator.java`**: Utilitário para preencher dinamicamente os menus pendentes (`ComboBox`) da interface de setup com metadados do mapa carregado.

### 🎮 2. Jogo (Painel Principal)

* **`GameViewController.java`**: Controlador principal da interface de jogo. Gere a alternância de turnos, apresenta feedback em tempo real ao utilizador e efetua a transição de estados visuais.
* **`GameViewDelegate.java`**: Interface de delegação utilizada para desacoplar o controlador principal dos manipuladores específicos da UI.
* **`MoveResultHandler.java`**: Analisa o objeto `MoveResult` recebido do motor e atualiza os elementos visuais correspondentes (mensagens de log, barras de HP, posição dos tokens).
* **`LeverRoomHandler.java`**: Gere especificamente a interação visual com salas de alavanca, desenhando as portas disponíveis e capturando a escolha do utilizador.
* **`EnigmaOverlayBuilder.java`**: Constrói overlays e janelas modais (*popups*) para exibição de enigmas, capturando a resposta de texto do utilizador.
* **`PlayerStrategy.java`**: Adaptador (padrão *Adapter*) que converte a seleção de estratégia feita na UI para o formato `BotStrategy` exigido pelo motor interno.

### 🎨 3. Renderizador do Mapa (`MapCanvasRenderer`)

Desenha dinamicamente o estado atual do mapa de jogo num componente `Canvas` do JavaFX.

* **`MapCanvasRenderer.java`**: Centraliza o desenho de nós, arestas, atalhos visíveis, tokens dos jogadores e animações ou efeitos visuais associados.
* **`BiomePoint.java`**: Representa um ponto de fundo no Canvas, associando coordenadas bidimensionais a um tipo específico de bioma para estilização personalizada do mapa.
* **`EdgeRenderer.java`**: Desenha as conexões e caminhos (corredores) entre as respetivas localizações.
* **`NodeRenderer.java`**: Desenha as salas individuais (`Location`), aplicando cores diferenciadas, ícones representativos e efeitos visuais destacados para salas com nível de perigo máximo (nível 5).
* **`NodePosition.java`**: Regista e mapeia as coordenadas exatas em pixels de cada nó no Canvas.
* **`PlayerLocationEntry.java`**: Par chave-valor composto por um `Player` e uma `Location`, utilizado para calcular a sobreposição e posicionamento correto dos tokens dos jogadores quando partilham o mesmo espaço.

### 🏆 4. Fim de Jogo (`Game Over`)

* **`GameOverController.java`**: Controlador do ecrã de encerramento do jogo. Exibe o grande vencedor, o percurso completo realizado pelo jogador vitorioso através do `fullPathHistory` e estatísticas de combate/exploração.

---

## 🛠️ Tema 7 — Utilitários (`engine/util/`)

Estruturas de suporte criadas especificamente para contornar a limitação de utilização de coleções nativas da API do Java (`java.util.*`), garantindo a conformidade com as restrições académicas/técnicas do projeto.

* **`SimpleRandom.java`**: Gerador pseudoaleatório próprio baseado numa semente (*seed*). Não utiliza a classe `java.util.Random`, o que assegura a reprodutibilidade exata de cenários em testes unitários.
* **`KeyValueEntry.java`**: Classe genérica que representa um par Chave-Valor (`Key-Value`). Substitui a necessidade de estruturas como o `HashMap` nativo quando combinada com listas customizadas.
* **`LocationStateEntry.java`**: Especialização de par contendo `(String id, LocationState state)` para mapeamento de estados de salas no motor do jogo.
* **`RespawnEntry.java`**: Especialização de par contendo `(playerId, respawnCount)` destinado ao controlo do número de vidas restantes de cada jogador.

---

## 📦 Tema 8 — Recursos (`resources/`)

Ficheiros de configuração, dados estruturados e layouts visuais que alimentam o comportamento do sistema.

### 📝 Bancos de Enigmas (`resources/enigmas/`)
* `enigmas.json`: Repositório geral e principal contendo todas as perguntas e respostas do jogo.
* `enigmas_cybersecurity.json`: Banco temático focado em cibersegurança.
* `enigmas_general.json`: Banco temático focado em cultura geral.
* `enigmas_minecraft.json`: Banco temático focado no universo Minecraft.

### 🗺️ Definições de Mapas (`resources/maps/`)
* `map_default.json`: Configuração padrão do mapa, contendo a estrutura de grafos, conexões nativas, papéis das salas e metadados visuais de posicionamento.
* `map_minecraft.json`: Mapa alternativo estilizado com biomas, nomes de localizações e ícones baseados em Minecraft.

### 🖼️ Interfaces Declarativas (`resources/ui/`)
Ficheiros FXML que definem a hierarquia de componentes visuais das janelas da aplicação:
* `setup.fxml`: Ecrã de configuração de parâmetros e criação de personagens.
* `game.fxml`: Tabuleiro de jogo, Canvas do mapa, logs e painéis de controlo de turnos.
* `gameover.fxml`: Painel de resultados, pódio e exibição dos históricos de movimentação.
