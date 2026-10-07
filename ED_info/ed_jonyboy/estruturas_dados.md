# 1. Coleções Lineares e Listas

## ArrayList
* **Breve Descrição:** É um array genérico que pode ser redimensionado automaticamente caso fique cheio, copiando os elementos para um novo array de maior capacidade.
* **Mapeamento nas Fichas Práticas:** FP01 (Parte II, Ex. 3 e 4).
* **Referência Cruzada nos Slides:** 2025.ED.Aula01.pdf (Slides 47 a 50).

## Listas Simplesmente Ligadas (LinkedList)
* **Breve Descrição:** Estrutura de dados dinâmica em que o tamanho aumenta e diminui conforme a necessidade. Utiliza referências (nós) para ligar um objeto ao seguinte numa sequência linear.
* **Mapeamento nas Fichas Práticas:** FP02 (Parte I, Ex. 1 e 2; Parte II, Ex. 1); FP07 (Parte I, Ex. 1; Parte II, Ex. 4 e 5).
* **Referência Cruzada nos Slides:** 2025.ED.Aula03.pdf (Slides 6 a 18).

## Listas Duplamente Ligadas (Doubly Linked List)
* **Breve Descrição:** Variação de uma lista ligada na qual cada nó possui duas referências: uma para o elemento seguinte e outra para o anterior, facilitando a navegação bidirecional.
* **Mapeamento nas Fichas Práticas:** FP02 (Parte I, Ex. 4; Parte II, Ex. 1 a 4); FP05 (Parte I, Ex. 2 e 5; Parte II, Ex. 3); FP06 (Parte I, Ex. 5 a 7); FP07 (Parte I, Ex. 2; Parte II, Ex. 3).
* **Referência Cruzada nos Slides:** 2024.ED.Aula06.pdf (Slides 29 a 34) e 2025.ED.Aula03.pdf (Slides 19 a 20).

## Listas Ordenadas (Ordered Lists)
* **Breve Descrição:** Coleções cujos elementos estão dispostos consoante uma característica intrínseca (ex: ordem alfabética ou ascendente), cabendo ao próprio elemento determinar o local onde será armazenado. Implementações: ArrayOrderedList e DoubleLinkedOrderedList.
* **Mapeamento nas Fichas Práticas:** FP05 (Parte I, Ex. 1 a 3).
* **Referência Cruzada nos Slides:** 2024.ED.Aula06.pdf (Slides 4 e 19).

## Listas Não Ordenadas (Unordered Lists)
* **Breve Descrição:** Coleções onde a ordem dos elementos não se baseia nas suas características, mas sim nas decisões do utilizador (ex: adicionar à frente, atrás, ou após um elemento). Implementações: ArrayUnorderedList e DoubleLinkedUnorderedList.
* **Mapeamento nas Fichas Práticas:** FP05 (Parte I, Ex. 4 e 5; Parte II, Ex. 4).
* **Referência Cruzada nos Slides:** 2024.ED.Aula06.pdf (Slides 6 e 20).

# 2. Estruturas LIFO e FIFO

## Pilhas (Stacks)
* **Breve Descrição:** Coleção não-linear cujos elementos entram e saem pela mesma extremidade (o topo), seguindo uma lógica LIFO (Last-in-First-out). Usada, por exemplo, na avaliação de expressões postfix. Implementações práticas: ArrayStack (usando arrays) e LinkedStack (usando listas ligadas).
* **Mapeamento nas Fichas Práticas:** FP03 (Parte I, Ex. 1 a 5; Parte II, Ex. 1 a 3); FP06 (Parte I, Ex. 3 e 8).
* **Referência Cruzada nos Slides:** 2025.ED.Aula04.pdf (Teoria Geral: Slides 3 a 14; ArrayStack: Slides 15 a 26; LinkedStack: Slides 28 a 39).

## Filas (Queues)
* **Breve Descrição:** Coleção processada de forma FIFO (First-in-First-out). Os elementos são adicionados na cauda (rear) e removidos da cabeça (front).
* **Mapeamento nas Fichas Práticas:** FP04 (Parte I, Ex. 1 a 5; Parte II, Ex. 1 a 5); FP06 (Parte I, Ex. 2 e 3).
* **Referência Cruzada nos Slides:** 2024.ED.Aula05.pdf (Teoria Geral: Slides 2 a 10; LinkedQueue: Slides 15 a 21; CircularArrayQueue: usar arrays circulares para evitar o shift de elementos O(n): Slides 23 a 30).

# 3. Estruturas em Árvore

## Árvores Binárias (Binary Trees)
* **Breve Descrição:** Estrutura não-linear hierárquica em que cada nó tem no máximo dois filhos (aridade 2). Suportam travessias em pré-ordem, em-ordem, pós-ordem e nível-ordem. Implementadas com referências dinâmicas (LinkedBinaryTree) ou arrays com índices calculados (ArrayBinaryTree).
* **Mapeamento nas Fichas Práticas:** FP09 (Parte I, Ex. 1 e 2; Parte II, Ex. 1).
* **Referência Cruzada nos Slides:** 2025.ED.Aula09.pdf (Teoria: Slides 2 a 34; LinkedBinaryTree: Slides 44 a 56; ArrayBinaryTree: Slides 57 a 63).

## Árvores Binárias de Pesquisa (Binary Search Trees - BST)
* **Breve Descrição:** Árvore binária ordenada onde os nós da sub-árvore esquerda são menores que o nó raiz, e os da sub-árvore direita são maiores ou iguais. Implementações: LinkedBinarySearchTree e ArrayBinarySearchTree.
* **Mapeamento nas Fichas Práticas:** FP10 (Parte I, Ex. 1 e 2; Parte II, Ex. 1 a 3).
* **Referência Cruzada nos Slides:** 2025.ED.Aula10.pdf (Slides 2 a 31).

## Árvores AVL (AVL Trees)
* **Breve Descrição:** Árvore de pesquisa binária balanceada que monitoriza o fator de balanceamento (diferença de altura entre sub-árvores direita e esquerda) com rotações (simples à direita/esquerda e duplas direita-esquerda/esquerda-direita) sempre que uma inserção ou remoção a desequilibra.
* **Mapeamento nas Fichas Práticas:** FP10 (Parte II, Ex. 4).
* **Referência Cruzada nos Slides:** 2025.ED.Aula10.pdf (Slides 45 a 51).

## Heaps (MinHeap)
* **Breve Descrição:** Árvore binária completa onde cada nó é sempre menor ou igual (no caso das minheaps) aos seus filhos esquerdo e direito, garantindo que o valor mínimo fica na raiz. Podem ser LinkedHeap ou ArrayHeap.
* **Mapeamento nas Fichas Práticas:** FP11 (Parte I, Ex. 1 a 3; Parte II, Ex. 1).
* **Referência Cruzada nos Slides:** 2025.ED.Aula11.pdf (Teoria e MinHeap: Slides 2 a 15; Implementações: Slides 25 a 48).

## Fila de Prioridade (Priority Queue)
* **Breve Descrição:** Uma coleção apoiada numa Heap em que os elementos são ordenados por duas regras: maior prioridade sai primeiro; e elementos com a mesma prioridade usam política FIFO.
* **Mapeamento nas Fichas Práticas:** FP11 (Parte I, Ex. 2; Parte II, Ex. 2).
* **Referência Cruzada nos Slides:** 2025.ED.Aula11.pdf (Slides 16 a 24).

# 4. Estruturas Não-Lineares de Relacionamento Livre

## Grafos (Graphs) e Redes (Weighted Graphs/Networks)
* **Breve Descrição:** Estrutura formada por vértices e arestas que permite ligações e relacionamentos arbitrários. Os grafos podem ser direcionados ou não direcionados e, se tiverem pesos (custos), designam-se por Redes. Suportam travessia em largura (BFS) e em profundidade (DFS).
* **Mapeamento nas Fichas Práticas:** FP12 (Parte I, Ex. 1 a 6; Parte II, Ex. 1 e 2).
* **Referência Cruzada nos Slides:** 2025.ED.Aula12.pdf (Teoria: Slides 2 a 17).

## Representações de Grafos: Matriz de Adjacências vs Lista de Adjacências
* **Breve Descrição:** Duas implementações práticas. A Lista de Adjacências cria um array/lista onde cada nó contém uma lista ligada de vizinhos. A Matriz de Adjacências cria uma grelha 2D booleana/pesada indexada aos vértices.
* **Mapeamento nas Fichas Práticas:** FP12 (Parte I, Ex. 1 e 4 referem a Matriz; Ex. 2 e 5 referem as Listas de Adjacências).
* **Referência Cruzada nos Slides:** 2025.ED.Aula12.pdf (Slides 46 a 52).

---
# Notas / Inconsistências Identificadas

* **SmackStackADT:** A estrutura SmackStackADT é requerida na Ficha Prática 6 (Parte I, Ex. 1), descrita como tendo o comportamento de uma stack normal com uma funcionalidade extra que elimina e devolve o último elemento da base da stack. No entanto, não existe nenhuma menção, definição formal teórica ou slide dedicado a esta estrutura específica no material teórico disponibilizado.
* **Listas Ligadas Circulares (CircularLinkedList e CircularDoubleLinkedList):** Na Ficha Prática 6 (Parte I, Ex. 4, 5, 6 e 7), é exigida a implementação e análise destas duas estruturas (onde o último elemento aponta para o primeiro). Nos slides teóricos, o conceito de "circularidade" é aplicado exclusivamente ao contexto das Filas com implementação em array (CircularArrayQueue). Não há slides a formalizar a estrutura ligada circular na unidade curricular de Estruturas de Dados.
* **Tabelas de Hash (Hash Tables):** Mencionada de forma muito breve (apenas num bullet point) no slide conceptual 2025.ED.Aula02.pdf (Slide 13), não existe qualquer Ficha Prática desenhada para esta matéria, nem qualquer detalhe adicional de implementação ou algoritmo em toda a restante documentação teórica disponibilizada.
