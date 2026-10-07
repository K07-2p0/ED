package structures.list;

import java.util.NoSuchElementException;

// Ex. 4 (Parte I) e Ex. 2, 3, 4 (Parte II) - lista duplamente ligada
public class DoublyLinkedList<T> {
    private DoubleNode<T> head;
    private DoubleNode<T> tail;
    private int count;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        count = 0;
    }

    // Insere um nó na cabeça
    public void addFirst(T element) {
        DoubleNode<T> node = new DoubleNode<T>(element);
        if (isEmpty()) {
            tail = node;
        } else {
            node.setNext(head);
            head.setPrevious(node);
        }
        head = node;
        count++;
    }

    // Insere um nó na cauda (usado no Ex. 3 da Parte II para manter a ordem)
    public void addLast(T element) {
        DoubleNode<T> node = new DoubleNode<T>(element);
        if (isEmpty()) {
            head = node;
        } else {
            node.setPrevious(tail);
            tail.setNext(node);
        }
        tail = node;
        count++;
    }

    // Remove o primeiro nó e devolve o elemento
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        T result = head.getElement();
        head = head.getNext();
        if (head == null) {
            tail = null;
        } else {
            head.setPrevious(null);
        }
        count--;
        return result;
    }

    // Remove o último nó e devolve o elemento (graças ao previous não é preciso percorrer a lista)
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        T result = tail.getElement();
        tail = tail.getPrevious();
        if (tail == null) {
            head = null;
        } else {
            tail.setNext(null);
        }
        count--;
        return result;
    }

    // Indica se a lista está vazia
    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    // Percorre e imprime todos os elementos
    public void print() {
        System.out.println(toString());
    }

    public String toString() {
        String result = "[";
        DoubleNode<T> current = head;
        while (current != null) {
            result += current.getElement();
            if (current.getNext() != null) {
                result += ", ";
            }
            current = current.getNext();
        }
        return result + "]";
    }

    // Parte II Ex. 2 - array com todos os elementos
    public Object[] toArray() {
        Object[] result = new Object[count];
        DoubleNode<T> current = head;
        int i = 0;
        while (current != null) {
            result[i] = current.getElement();
            current = current.getNext();
            i++;
        }
        return result;
    }

    // Parte II Ex. 2 - array dos elementos desde o início até à posição (inclusive)
    public Object[] toArrayUntil(int position) {
        return toArrayBetween(0, position);
    }

    // Parte II Ex. 2 - array dos elementos depois da posição (a posição não entra)
    public Object[] toArrayAfter(int position) {
        if (position < 0 || position >= count) {
            throw new IndexOutOfBoundsException("Posicao invalida: " + position);
        }
        if (position == count - 1) {
            return new Object[0];
        }
        return toArrayBetween(position + 1, count - 1);
    }

    // Parte II Ex. 2 - array dos elementos entre duas posições (ambas inclusive)
    public Object[] toArrayBetween(int start, int end) {
        if (start < 0 || end >= count || start > end) {
            throw new IndexOutOfBoundsException("Intervalo invalido: " + start + " a " + end);
        }
        Object[] result = new Object[end - start + 1];

        // Avança até à posição inicial
        DoubleNode<T> current = head;
        for (int i = 0; i < start; i++) {
            current = current.getNext();
        }

        for (int i = 0; i < result.length; i++) {
            result[i] = current.getElement();
            current = current.getNext();
        }
        return result;
    }

    // Parte II Ex. 3 - nova lista só com os elementos pares (quando os elementos são Integer)
    public DoublyLinkedList<T> getEvenElements() {
        DoublyLinkedList<T> result = new DoublyLinkedList<T>();
        DoubleNode<T> current = head;
        while (current != null) {
            T element = current.getElement();
            if (element instanceof Integer && (Integer) element % 2 == 0) {
                result.addLast(element);
            }
            current = current.getNext();
        }
        return result;
    }

    // Parte II Ex. 4 - conta quantos elementos são iguais ao dado
    public int countOccurrences(T element) {
        int total = 0;
        DoubleNode<T> current = head;
        while (current != null) {
            if (current.getElement().equals(element)) {
                total++;
            }
            current = current.getNext();
        }
        return total;
    }

    // Parte II Ex. 4 - remove todos os elementos iguais ao dado; devolve quantos removeu
    public int removeAllOccurrences(T element) {
        int removed = 0;
        DoubleNode<T> current = head;
        while (current != null) {
            // Guardar o seguinte antes de desligar o nó atual
            DoubleNode<T> next = current.getNext();

            if (current.getElement().equals(element)) {
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    removeLast();
                } else {
                    // Nó do meio: o anterior e o seguinte passam a apontar um para o outro
                    current.getPrevious().setNext(next);
                    next.setPrevious(current.getPrevious());
                    count--;
                }
                removed++;
            }
            current = next;
        }
        return removed;
    }
}
