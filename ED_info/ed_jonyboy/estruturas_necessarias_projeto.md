# Estruturas de Dados Essenciais para o "Labirinto da Glória"

Tendo em conta que a "Escolha e utilização adequada das estruturas de dados" é o critério principal de avaliação do projeto, apresentamos o conjunto mínimo e ideal de estruturas que a API deve ter implementadas para desenvolver o jogo com toda a robustez e conforto.

## 1. Grafos e Redes (Graphs / Networks) 🔴 **[Essencial]**
* **Para que serve:** Representar a planta e o mapa físico do jogo.
* **Aplicação no Projeto:** O labirinto é modelado perfeitamente como um grafo. As "divisões" (entradas, sala do tesouro, salas de enigmas) são os vértices (nós) e os "corredores" que as unem são as arestas. 
* **Implementação na API:** `Graph` (para labirintos simples onde todas as distâncias são iguais) ou `Network` (caso os corredores tenham distâncias ou pesos diferentes para dificultar).

## 2. Filas de Prioridade e Caminhos Mais Curtos (Priority Queues) 🔴 **[Essencial]**
* **Para que serve:** O motor principal para a navegação automática e a Inteligência Artificial (Bots).
* **Aplicação no Projeto:** O modo de jogo automático exige que existam *bots* a seguir estratégias para ganhar. Para um bot saber matematicamente qual o melhor caminho/direção para chegar ao centro do labirinto (tesouro), precisará de um algoritmo de caminho mais curto (como o Dijkstra).
* **Implementação na API:** A estrutura `PriorityQueue` assente num `MinHeap`, utilizada nativamente no cálculo do método `shortestPathWeight` e `iteratorShortestPath`.

## 3. Pilhas (Stacks) 🔴 **[Essencial]**
* **Para que serve:** Registo de históricos, *logs* e eventos de retrocesso.
* **Aplicação no Projeto:** O jogo tem "eventos aleatórios" que obrigam o jogador a recuar casas (fazendo o percurso inverso). Além disso, é exigida a geração de um relatório final com todo o trajeto do jogador. 
* **Implementação na API:** `LinkedStack` ou `ArrayStack`. A cada avanço de sala, faz-se um *push* da divisão; para "recuar 3 casas", basta fazer *pop* 3 vezes. A pilha retém a memória cronológica perfeita de trás para a frente.

## 4. Filas (Queues) 🔴 **[Essencial]**
* **Para que serve:** Gestão do fluxo da partida, rondas e turnos.
* **Aplicação no Projeto:** Tratando-se de um jogo disputado em "turnos sequenciais", a vez de cada jogador tem de ser rigidamente controlada. Quem está na frente da fila (cabeça) joga e, após concluir a sua jogada, é removido e colocado no fim da fila (cauda) para esperar pela próxima ronda.
* **Implementação na API:** `LinkedQueue` ou `CircularArrayQueue`.

## 5. Listas Não Ordenadas (Unordered Lists) 🔴 **[Essencial]**
* **Para que serve:** Gestão geral e genérica de elementos (inventários, posições, entidades ativas).
* **Aplicação no Projeto:** Serão os autênticos "faz-tudo" do motor de jogo para alojar as *pools* de dados: a lista de jogadores ativos numa sessão, as alavancas presentes dentro de uma determinada divisão, os efeitos a decorrer num corredor, ou as posições de entrada disponíveis no mapa.
* **Implementação na API:** `ArrayUnorderedList` ou `DoublyLinkedList`.

---

## 6. Listas Circulares (Circular Lists) 🟡 **[Muito Recomendado]**
* **Para que serve:** Gestão de "baralhos de cartas", roletas de efeitos e eventos cíclicos.
* **Aplicação no Projeto:** O enunciado foca que os enigmas "só podem repetir quando todas já tiverem sido usadas". A forma computacional mais elegante de gerir isto é carregar os enigmas, baralhar e inseri-los numa lista circular. O jogo retira o enigma da cabeça da lista; a própria estrutura garante que ele só volta a aparecer quando der a volta inteira, sem recurso a *if/elses* ou lógicas complexas para reiniciar índices.
* **Implementação na API:** `CircularLinkedList` ou `CircularDoubleLinkedList`.

## 7. Tabelas de Hash / Dicionários (Hash Maps) 🔵 **[Extra / Upgrade de Conforto]**
* **Para que serve:** *Parsing* e leitura ultra-rápida do mapa e das ligações a partir do JSON.
* **Aplicação no Projeto:** Na tradução/conversão dos ficheiros JSON para código Java, terás obrigatoriamente de ligar identificadores em formato de texto (Ex: id: `"sala_01"`) aos teus objetos/classes reais Java (`Sala`). Sem um mapa de chaves-valores rápido, isto obriga a fazer procuras lineares $O(n)$ constantes.
* **Nota face à API:** Esta estrutura **não** existe nativamente na `API_ED` fornecida. Se a sua criação não for uma exigência dos professores, pode ser mitigada e substituída por uma Lista Não Ordenada preenchida com uma classe de pares de `Chave/Valor` feita por ti, trocando um pouco de performance por facilidade de implementação.
