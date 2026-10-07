package structures.queue;

import structures.common.EmptyCollectionException;
import structures.common.LinearNode;

// Ex. 1 - queue implementada com uma lista ligada (slides 15 a 21)
public class LinkedQueue<T> implements QueueADT<T> {

    // Primeiro nó da lista (frente da fila)
    private LinearNode<T> front;

    // Último nó da lista (traseira da fila)
    private LinearNode<T> rear;

    // Número de elementos
    private int count;

    public LinkedQueue() {
        front = null;
        rear = null;
        count = 0;
    }

    // O novo nó é ligado a seguir ao rear e passa a ser o rear (slide 18)
    public void enqueue(T element) {
        LinearNode<T> node = new LinearNode<T>(element);
        if (isEmpty()) {
            front = node;
        } else {
            rear.setNext(node);
        }
        rear = node;
        count++;
    }

    // O front passa a ser o nó seguinte (slide 19)
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue");
        }
        T result = front.getElement();
        front = front.getNext();
        count--;
        if (isEmpty()) {
            rear = null;
        }
        return result;
    }

    // Devolve o elemento da frente sem o remover (slide 20)
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue");
        }
        return front.getElement();
    }

    // Vazia quando o contador é 0 (slide 20)
    public boolean isEmpty() {
        return count == 0;
    }

    // Devolve o contador (slide 21)
    public int size() {
        return count;
    }

    // Percorre a lista da frente para a traseira (slide 21)
    public String toString() {
        String result = "[";
        LinearNode<T> current = front;
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
