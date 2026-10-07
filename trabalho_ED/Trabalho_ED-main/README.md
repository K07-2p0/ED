# Labirinto da Glória: Jogo de Aventura em Consola

Este projeto consiste na implementação de um jogo de aventura em labirinto baseado em turnos, desenvolvido em Java. O foco principal é a aplicação prática e a manipulação de várias Estruturas de Dados (ED) para a modelação do grafo (labirinto), a gestão de turnos e o registo do histórico dos jogadores.

## 🚀 Requisitos

Para compilar e executar o projeto, são necessários os seguintes requisitos:

* **Java Development Kit (JDK):** Versão 11 ou superior.
* **API de Estruturas de Dados:** O projeto depende de uma API externa fornecida (`API_ED.jar`) que contém as implementações das Estruturas de Dados utilizadas.

## 🛠️ Estruturas de Dados Chave

A base do jogo é construída sobre as seguintes estruturas de dados da API:

| Estrutura | Classe da API | Utilização no Projeto |
|:---|:---|:---|
| **Grafo/Rede** | `Network` / `NetworkADT` | Modela o Labirinto. As Divisões são os **vértices** e os Corredores são as **arestas**. |
| **Fila** | `CircularArrayQueue` / `QueueADT` | Usada no `MotorJogo` para gerir a ordem sequencial dos turnos dos jogadores (FIFO). |
| **Lista** | `DoublyLinkedList` / `ListADT` | Usada para registar o percurso, enigmas resolvidos e logs de eventos no `Historico` do jogador. |
| **Algoritmo de Busca** | BFS (Breadth-First Search) | Implementado no `GestorMapa` para calcular a distância mais curta (em número de divisões) até à `SalaTesouro`. |

## 🧑‍💻 Estrutura da API
```
api_ED/
├── Exceptions/
│   ├── ElementNotFoundException.java
│   └── EmptyCollectionException.java
├── Graphs/
│   ├── Graph.java
│   ├── GraphADT.java
│   ├── Network.java
│   └── NetworkADT.java
├── Lists/
│   ├── DoubleNode.java
│   ├── DoublyLinkedList.java
│   └── ListADT.java
├── Queues_Stacks/
│   ├── CircularArrayQueue.java
│   ├── LinkedListStack.java
│   ├── QueueADT.java
│   └── StackADT.java
└── Trees_Heaps/
    ├── ArrayHeap.java
    ├── HeapADT.java
    ├── PriorityQueue.java
    └── PriorityQueueNode.java
```

## ✨ Funcionalidades do Jogo

O jogo é modular e integra várias classes de domínio:

### 1. Entidades do Labirinto (`core.model.divisao`)

O labirinto é composto por diferentes tipos de divisões (vértices), que definem a interação do jogador:

* `PontoPartida`: Locais de início. A entrada é sempre bem-sucedida.
* `SalaEnigma`: Requer a resolução de um `Enigma` para abrir a passagem, concedendo pontos ao jogador em caso de acerto.
* `SalaAlavanca`: Permite que o jogador tente ativar uma alavanca que pode ter um efeito positivo (como desbloquear corredores) ou negativo (como bloqueio de turno).
* `SalaTesouro`: O objetivo final e condição de vitória do jogo.

### 2. Eventos e Efeitos

Os Corredores (`Corredor.java`) podem acionar `Eventos` aleatórios. Os efeitos (tipos de `Evento`) incluem:

* **Bloqueio:** `BLOQUEIO_TURNO`.
* **Movimento:** `AVANCAR`, `RECUAR`.
* **Posição:** `TROCA_POSICAO`, `TROCA_GLOBAL`.
* **Turnos:** `JOGADA_EXTRA` (permite jogar novamente).

### 3. I/O e Relatórios

O projeto inclui um parser JSON manual (`LeitorJSON.java`) para carregar mapas e configurações.

Ao terminar a partida (Vitória), é possível gerar relatórios em JSON através do `ExportadorJSON.java`:

* **Relatório Individual:** Detalha o histórico e percurso de um jogador.
* **Relatório Global:** Agrega os dados de todos os jogadores da partida.

## 🧑‍💻 Estrutura do Código

A arquitetura do projeto segue a separação de responsabilidades (Domínio/Lógica/I/O/UI):

```
Trabalho_ED/
├── .vscode/
│   └── settings.json
├── lib/
│   └── API_ED.jar
├── resources/
│   ├── enigmas/
│   │   └── enigma_1.json
│   ├── eventos/
│   │   ├── eventos.json
│   │   ├── eventos_globais.txt
│   │   └── eventos_jogador.txt
│   └── mapas/
│       ├── labirinto_1.json
│       ├── labirinto_2.json
│       └── labirinto_3.json
│       
├── src/
│   ├── core/
│   │   ├── game/
│   │   │   ├── GameLoader.java
│   │   │   ├── GestorMapa.java
│   │   │   ├── MotorJogo.java
│   │   │   ├── MovimentoStrategy.java
│   │   │   ├── PlayerInteraction.java (Alterado para Menu Global)
│   │   │   └── TurnProcessor.java
│   │   ├── io/
│   │   │   ├── ExportadorJSON.java (Alterado com método Global)
│   │   │   └── LeitorJSON.java
│   │   ├── model/
│   │   │   ├── ator/
│   │   │   │   ├── Historico.java
│   │   │   │   ├── Jogador.java
│   │   │   │   ├── JogadorBot.java
│   │   │   │   └── JogadorHumano.java
│   │   │   ├── divisao/
│   │   │   │   ├── Divisao.java
│   │   │   │   ├── PontoPartida.java
│   │   │   │   ├── SalaAlavanca.java
│   │   │   │   ├── SalaEnigma.java
│   │   │   │   └── SalaTesouro.java
│   │   │   └── itens/
│   │   │       ├── Corredor.java
│   │   │       ├── Enigma.java
│   │   │       └── Evento.java
│   │   └── ui/
│   │       ├── EditorMapa.java
│   │       └── Menu.java
│   └── Main.java
└── README.md
```

## ▶️ Como Jogar

1. **Executar:** Inicie o programa executando a classe `Main.java`.
2. **Lobby:** No `Menu`, selecione **[1] JOGAR** para aceder ao Lobby.
3. **Adicionar Jogadores:** Adicione Jogadores Humanos e/ou preencha com Bots (máximo de 4).
4. **Mapa:** Certifique-se de que um mapa está selecionado em `resources/mapas/`.
5. **Iniciar:** Selecione **[4] !!! INICIAR JOGO !!!** para começar a partida.
6. **Turnos:** Durante o jogo, digite `turno` ou pressione **ENTER** para processar o próximo turno.





**Desenvolvido com ☕ e Java**