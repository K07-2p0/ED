package Trees_Heaps;

import Exceptions.EmptyCollectionException;

public class PriorityQueue<T> {
    private ArrayHeap<PriorityQueueNode<T>> heap;
    
    public PriorityQueue() {
        heap = new ArrayHeap<>();
    }
    
    /**
     * Adiciona elemento com prioridade double
     */
    public void enqueue(T element, double priority) { // Alterado de int para double
        PriorityQueueNode<T> node = new PriorityQueueNode<>(element, priority);
        heap.addElement(node);
    }
    
    // Os restantes métodos (dequeue, first, isEmpty, size) mantêm-se iguais
    public T dequeue() throws EmptyCollectionException {
        PriorityQueueNode<T> node = heap.removeMin();
        return node.getElement();
    }
    
    public T first() throws EmptyCollectionException {
        PriorityQueueNode<T> node = heap.findMin();
        return node.getElement();
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    public int size() {
        return heap.size();
    }
    
    /** 
    @Override
    public String toString() {
        return heap.toString();
    }
    */

}