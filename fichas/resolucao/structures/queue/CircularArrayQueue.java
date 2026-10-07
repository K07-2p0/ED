package structures.queue;

import structures.common.EmptyCollectionException;

// Ex. 2 - queue implementada com um array circular (slides 24 a 28)
public class CircularArrayQueue<T> implements QueueADT<T> {

    // Capacidade inicial por defeito
    private final int DEFAULT_CAPACITY = 10;

    // Índice do primeiro elemento (frente da fila)
    private int front;

    // Índice da próxima posição livre (traseira da fila)
    private int rear;

    // Número de elementos
    private int count;

    // Array que guarda os elementos; o último índice precede o índice 0
    private T[] queue;

    // Não se pode fazer new T[], por isso cria-se um Object[] e faz-se cast
    public CircularArrayQueue() {
        front = 0;
        rear = 0;
        count = 0;
        queue = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public CircularArrayQueue(int initialCapacity) {
        front = 0;
        rear = 0;
        count = 0;
        queue = (T[]) (new Object[initialCapacity]);
    }

    // Guarda na posição rear e avança o rear em círculo (slide 28)
    public void enqueue(T element) {
        if (size() == queue.length) {
            expandCapacity();
        }
        queue[rear] = element;

        // o que o torna um array circular
        rear = (rear + 1) % queue.length;
        count++;
    }

    // Remove o elemento do front e avança o front em círculo, sem deslocar
    // elementos
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue");
        }
        T result = queue[front];
        queue[front] = null;

        // o que o torna um array circular
        front = (front + 1) % queue.length;
        count--;
        return result;
    }

    // Devolve o elemento da frente sem o remover
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue");
        }
        return queue[front];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    // Elementos da frente para a traseira (pode passar do último índice para o 0)
    public String toString() {
        String result = "[";
        for (int i = 0; i < count; i++) {
            result += queue[(front + i) % queue.length];
            if (i < count - 1) {
                result += ", ";
            }
        }
        return result + "]";
    }

    // Cria um array com o dobro da capacidade e copia os elementos a partir
    // do front, ficando a fila "desenrolada" (front = 0, rear = count)
    private void expandCapacity() {
        T[] larger = (T[]) (new Object[queue.length * 2]);
        for (int i = 0; i < count; i++) {
            larger[i] = queue[(front + i) % queue.length];
        }
        queue = larger;
        front = 0;
        rear = count;
    }
}
