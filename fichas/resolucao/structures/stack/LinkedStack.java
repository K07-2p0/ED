package structures.stack;

import structures.common.EmptyCollectionException;
import structures.common.LinearNode;

// Ex. 2, 3 e 4 - stack implementada com uma lista ligada (slides 31 a 38)
public class LinkedStack<T> implements StackADT<T> {

    // Nó do topo (o primeiro da lista)
    private LinearNode<T> top;

    // Número de elementos
    private int count;

    public LinkedStack() {
        top = null;
        count = 0;
    }

    // Ex. 3 - o novo nó aponta para o antigo topo e passa a ser o topo (slide 34)
    public void push(T element) {
        LinearNode<T> node = new LinearNode<T>(element);
        node.setNext(top);
        top = node;
        count++;
    }

    // Ex. 3 - o topo passa a ser o nó seguinte (slide 35)
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }
        T result = top.getElement();
        top = top.getNext();
        count--;
        return result;
    }

    // Ex. 4 - devolve o elemento do topo sem o remover
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }
        return top.getElement();
    }

    // Ex. 4 - vazia quando o contador é 0
    public boolean isEmpty() {
        return count == 0;
    }

    // Ex. 4 - devolve o contador
    public int size() {
        return count;
    }

    // Ex. 4 - percorre a lista do topo para o fundo
    public String toString() {
        String result = "[";
        LinearNode<T> current = top;
        while (current != null) {
            result += current.getElement();
            if (current.getNext() != null) {
                result += ", ";
            }
            current = current.getNext();
        }
        return result + "]";
    }
}
