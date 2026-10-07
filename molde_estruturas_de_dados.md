# Molde para criar qualquer estrutura de dados (Java)

> Os nomes (`StackADT`, `LinearNode`, `EmptyCollectionException`...) seguem a convenção habitual nesta cadeira. Ajusta-os ao que o professor usar nas aulas.

---

## Parte 1 — O esqueleto comum (serve para todas)

Qualquer estrutura tem estas 6 peças. Preenche-as por ordem.

### 1. Interface (ADT): o contrato
Só assinaturas e comentários. Diz **o que** a estrutura faz, nunca **como**.

```java
public interface NomeADT<T> {
    // operações de inserção
    // operações de remoção
    // operações de consulta (sem alterar)
    // operações comuns (ver abaixo)
}
```

**Operações comuns a quase todas:**

| Método | Devolve | Função |
|---|---|---|
| `isEmpty()` | `boolean` | Está vazia? |
| `size()` | `int` | Número de elementos |
| `toString()` | `String` | Representação para imprimir |
| `iterator()` | `Iterator<T>` | Percorrer os elementos (listas, árvores, grafos) |

#### Nota: iterador fail-fast (`modCount` / `expectedModCount`)
Se a estrutura for alterada enquanto um iterador a percorre, o iterador fica inconsistente. O padrão do Java é detetar isso e falhar logo:

- A estrutura tem um atributo `int modCount`, incrementado em **todas** as operações que a alteram (inserir, remover). Consultas não mexem nele.
- Ao criar o iterador, este guarda `expectedModCount = modCount`.
- Em cada `hasNext()` / `next()`, o iterador compara os dois. Se forem diferentes, lança `ConcurrentModificationException`.

```java
private class MeuIterator implements Iterator<T> {
    private int current = 0;
    private int expectedModCount = modCount;

    public boolean hasNext() {
        if (expectedModCount != modCount)
            throw new ConcurrentModificationException();
        return current < count;
    }

    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        return elementos[current++];
    }
}
```

O `remove()` do próprio iterador é a exceção: altera a estrutura mas atualiza também o `expectedModCount`, por isso é a forma segura de remover durante uma iteração.

### 2. Nó (só nas implementações ligadas)

```java
public class LinearNode<T> {
    private T element;
    private LinearNode<T> next;      // lista duplamente ligada: + previous
    // construtores, getters e setters
}
```

Árvores usam `BinaryTreeNode<T>` com `left` e `right`.

### 3. Classe de implementação

```java
public class ArrayNome<T> implements NomeADT<T>   { ... }   // versão com array
public class LinkedNome<T> implements NomeADT<T>  { ... }   // versão com nós
```

### 4. Atributos

| Versão array | Versão ligada |
|---|---|
| `T[] elementos` | `LinearNode<T> head` / `top` |
| `int count` | `LinearNode<T> tail` (se precisar do fim) |
| `DEFAULT_CAPACITY` | `int count` |
| `int modCount` (se tiver iterador) | `int modCount` (se tiver iterador) |

Criar o array genérico: `elementos = (T[]) new Object[DEFAULT_CAPACITY];`

### 5. Construtores
- Sem argumentos (capacidade por omissão).
- Com capacidade inicial (só versão array).

### 6. Métodos auxiliares e exceções
- `expandCapacity()` (versão array): cria um array com o dobro do tamanho e copia os elementos.
- `EmptyCollectionException`: lançar ao remover ou consultar numa estrutura vazia.
- `ElementNotFoundException`: lançar quando o elemento procurado não existe.

### Checklist final de cada estrutura
- [ ] Interface com todas as operações comentadas
- [ ] Classe genérica `<T>` que implementa a interface
- [ ] Atributos e construtores
- [ ] Cada operação trata o caso **vazio**
- [ ] Cada operação trata o caso de **um só elemento**
- [ ] Versão array: trata o caso **cheio** (`expandCapacity`)
- [ ] `count` atualizado em todas as inserções e remoções
- [ ] `modCount` incrementado em todas as alterações (se tiver iterador)
- [ ] Big-O de cada operação anotado
- [ ] Classe `Demo` com `main` para testar

---

## Parte 2 — O que muda em cada estrutura

### Pilha (Stack) — LIFO
Só se mexe no **topo**. Não há pesquisa.

| Método | Função | Array | Ligada |
|---|---|---|---|
| `push(T e)` | Coloca no topo | O(1)* | O(1) |
| `pop()` | Remove e devolve o topo | O(1) | O(1) |
| `peek()` | Devolve o topo sem remover | O(1) | O(1) |

\* O(n) quando é preciso expandir; em média O(1).

Implementações: `ArrayStack`, `LinkedStack`.

---

### Fila (Queue) — FIFO
Insere-se **atrás**, remove-se **à frente**. Não há pesquisa.

| Método | Função | Array circular | Ligada |
|---|---|---|---|
| `enqueue(T e)` | Insere no fim | O(1)* | O(1) |
| `dequeue()` | Remove e devolve o da frente | O(1) | O(1) |
| `first()` | Devolve o da frente sem remover | O(1) | O(1) |

Atributos extra: `front` e `rear` (array circular: `rear = (rear + 1) % elementos.length`); `head` e `tail` (ligada).

Implementações: `LinkedQueue`, `CircularArrayQueue`.

---

### Lista (List) — acesso livre
Pode inserir, remover e **pesquisar** em qualquer posição.

**Operações comuns a todas as listas (`ListADT`):**

| Método | Função |
|---|---|
| `removeFirst()` | Remove e devolve o primeiro |
| `removeLast()` | Remove e devolve o último |
| `remove(T e)` | Remove um elemento específico |
| `first()` / `last()` | Consultar as pontas |
| `contains(T e)` | Pesquisa: existe? — O(n) |

**O que muda em cada variante (a inserção):**

| Variante | Métodos de inserção | Regra |
|---|---|---|
| Não ordenada | `addToFront(T e)`, `addToRear(T e)`, `addAfter(T e, T alvo)` | Onde o utilizador quiser |
| Ordenada | `add(T e)` | Posição definida pela ordem; `T` tem de ser `Comparable` |
| Indexada | `add(int i, T e)`, `get(int i)`, `set(int i, T e)`, `indexOf(T e)`, `remove(int i)` | Por posição numérica |

Implementações: `ArrayList` (array), lista simplesmente ligada (`next`), lista duplamente ligada (`next` + `previous`, facilita `removeLast`).

---

### Fila de Prioridade (Priority Queue)
Sai primeiro o de **maior prioridade**, não o primeiro a chegar. Normalmente implementada com um **heap**.

| Método | Função |
|---|---|
| `addElement(T e, int prioridade)` | Insere com prioridade |
| `removeNext()` | Remove o de maior prioridade |

---

### Árvore Binária
Cada nó tem no máximo 2 filhos. Nó: `element`, `left`, `right`. Atributo: `root`.

| Método | Função |
|---|---|
| `getRootElement()` | Devolve a raiz |
| `contains(T e)` / `find(T e)` | Pesquisa — O(n) |
| `iteratorInOrder()` | esquerda → nó → direita |
| `iteratorPreOrder()` | nó → esquerda → direita |
| `iteratorPostOrder()` | esquerda → direita → nó |
| `iteratorLevelOrder()` | nível a nível (usa uma queue) |

---

### Árvore Binária de Pesquisa (BST)
Herda da árvore binária + **regra**: menores à esquerda, maiores à direita. `T extends Comparable`.

| Método | Função | Médio | Pior (degenerada) |
|---|---|---|---|
| `addElement(T e)` | Insere respeitando a ordem | O(log n) | O(n) |
| `removeElement(T e)` | Remove e reorganiza | O(log n) | O(n) |
| `find(T e)` | Pesquisa | O(log n) | O(n) |
| `findMin()` / `findMax()` | Mais à esquerda / mais à direita | O(log n) | O(n) |
| `removeMin()` / `removeMax()` | Remove o menor / maior | O(log n) | O(n) |

**AVL e Red-Black:** mesma interface da BST + **rotações** depois de inserir ou remover para manter a árvore equilibrada, garantindo O(log n) sempre.
- AVL: guardar o **fator de equilíbrio** (ou altura) em cada nó.
- Red-Black: guardar a **cor** em cada nó.

---

### Heap
Árvore binária **completa** em que cada pai é menor (MinHeap) ou maior (MaxHeap) que os filhos. `T extends Comparable`.

| Método | Função | Big-O |
|---|---|---|
| `addElement(T e)` | Insere no fim e sobe até ao sítio certo | O(log n) |
| `removeMin()` | Remove a raiz e reorganiza (desce) | O(log n) |
| `findMin()` | Devolve a raiz | O(1) |

MaxHeap: trocar por `removeMax()` / `findMax()`.
Implementações: `ArrayHeap` (filhos de `i` em `2i+1` e `2i+2`), `LinkedHeap`.

---

### Tabela de Hash / Map / Set
Pesquisa por **chave**, sem percorrer tudo.

**Map** (chave → valor, `<K, V>`):

| Método | Função | Hash (médio) | Árvore (TreeMap) |
|---|---|---|---|
| `put(K k, V v)` | Associa valor à chave | O(1) | O(log n) |
| `get(K k)` | Devolve o valor da chave | O(1) | O(log n) |
| `remove(K k)` | Remove a entrada | O(1) | O(log n) |
| `containsKey(K k)` | A chave existe? | O(1) | O(log n) |

**Set** (sem repetidos, `<T>`): `add(T e)`, `remove(T e)`, `contains(T e)`.

Específico do hash:
- Função `hash(K k)` que calcula o índice no array.
- Tratamento de **colisões** (cada posição com uma pequena lista ligada, ou procurar a posição livre seguinte).
- `rehash()` quando a tabela fica demasiado cheia.

Implementações Java: `HashMap` / `HashSet` (hash); `TreeMap` / `TreeSet` (árvore Red-Black, mantêm ordem).

---

### Grafo (Graph)
Vértices ligados por arestas.

| Método | Função |
|---|---|
| `addVertex(T v)` / `removeVertex(T v)` | Gerir vértices |
| `addEdge(T v1, T v2)` / `removeEdge(T v1, T v2)` | Gerir arestas |
| `iteratorBFS(T inicio)` | Travessia em largura (usa queue) |
| `iteratorDFS(T inicio)` | Travessia em profundidade (usa stack) |
| `iteratorShortestPath(T a, T b)` | Caminho mais curto |
| `isConnected()` | O grafo é conexo? |

**Variantes:**
- Não dirigido: `addEdge(a, b)` liga nos dois sentidos.
- Dirigido: `addEdge(a, b)` liga só de `a` para `b`.
- Pesado (rede / `NetworkADT`): `addEdge(T v1, T v2, double peso)`, `shortestPathWeight(T a, T b)`, `mstNetwork()` (árvore geradora de custo mínimo).

**Armazenamento:**

| | Matriz de adjacências | Lista de adjacências |
|---|---|---|
| Atributos | `T[] vertices`, `boolean[][] adjMatrix` (ou `double[][]` se pesado) | `T[] vertices`, array de listas de vizinhos |
| Verificar se há aresta | O(1) | O(nº vizinhos) |
| Memória | O(V²) | O(V + E) |
| Melhor para | Grafos densos | Grafos esparsos |

---

## Parte 3 — Resumo: qual o armazenamento de cada uma

| Estrutura | Array | Nós ligados |
|---|---|---|
| Stack | `ArrayStack` | `LinkedStack` |
| Queue | `CircularArrayQueue` | `LinkedQueue` |
| Lista | `ArrayList` | Simples / duplamente ligada |
| Árvore / BST | Possível (pouco usado) | Mais comum |
| Heap | `ArrayHeap` (mais comum) | `LinkedHeap` |
| Hash | Array de baldes | Listas em cada balde |
| Grafo | Matriz de adjacências | Lista de adjacências |
