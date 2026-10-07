package Queues_Stacks;

import Exceptions.EmptyCollectionException;

/**
 * Implementação de uma fila circular usando array
 */
public class CircularArrayQueue<T> implements QueueADT<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] queue;
    private int front;
    private int rear;
    private int count;
    
    @SuppressWarnings("unchecked")
    public CircularArrayQueue() {
        queue = (T[]) new Object[DEFAULT_CAPACITY];
        front = 0;
        rear = 0;
        count = 0;
    }
    
    @Override
    public void enqueue(T element) {
        if (size() == queue.length) {
            expandCapacity();
        }
        
        queue[rear] = element;
        rear = (rear + 1) % queue.length;
        count++;
    }
    
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Fila vazia");
        }
        
        T element = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;
        count--;
        
        return element;
    }
    
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Fila vazia");
        }
        return queue[front];
    }
    
    @Override
    public boolean isEmpty() {
        return count == 0;
    }
    
    @Override
    public int size() {
        return count;
    }
    
    @SuppressWarnings("unchecked")
    private void expandCapacity() {
        T[] larger = (T[]) new Object[queue.length * 2];
        
        for (int i = 0; i < count; i++) {
            larger[i] = queue[front];
            front = (front + 1) % queue.length;
        }
        
        front = 0;
        rear = count;
        queue = larger;
    }
    

    /* 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[Frente] ");
        
        for (int i = 0; i < count; i++) {
            int index = (front + i) % queue.length;
            sb.append(queue[index]);
            if (i < count - 1) {
                sb.append(" <- ");
            }
        }
        
        sb.append(" [Trás]");
        return sb.toString();
    }
    */
}