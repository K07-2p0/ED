package Trees_Heaps;

import Exceptions.EmptyCollectionException;

/**
 * Implementação de um Min-Heap usando array
 */
public class ArrayHeap<T extends Comparable<T>> implements HeapADT<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] heap;
    private int count;
    
    @SuppressWarnings("unchecked")
    public ArrayHeap() {
        heap = (T[]) new Comparable[DEFAULT_CAPACITY];
        count = 0;
    }
    
    @Override
    public void addElement(T element) {
        if (count == heap.length) {
            expandCapacity();
        }
        
        heap[count] = element;
        heapifyUp(count);
        count++;
    }
    
    @Override
    public T removeMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Heap vazio");
        }
        
        T min = heap[0];
        heap[0] = heap[count - 1];
        heap[count - 1] = null;
        count--;
        
        if (count > 0) {
            heapifyDown(0);
        }
        
        return min;
    }
    
    @Override
    public T findMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Heap vazio");
        }
        return heap[0];
    }
    
    @Override
    public boolean isEmpty() {
        return count == 0;
    }
    
    @Override
    public int size() {
        return count;
    }
    
    private void heapifyUp(int index) {
        if (index > 0) {
            int parent = (index - 1) / 2;
            
            if (heap[index].compareTo(heap[parent]) < 0) {
                T temp = heap[index];
                heap[index] = heap[parent];
                heap[parent] = temp;
                heapifyUp(parent);
            }
        }
    }
    
    private void heapifyDown(int index) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int smallest = index;
        
        if (left < count && heap[left].compareTo(heap[smallest]) < 0) {
            smallest = left;
        }
        
        if (right < count && heap[right].compareTo(heap[smallest]) < 0) {
            smallest = right;
        }
        
        if (smallest != index) {
            T temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;
            heapifyDown(smallest);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void expandCapacity() {
        T[] larger = (T[]) new Comparable[heap.length * 2];
        System.arraycopy(heap, 0, larger, 0, count);
        heap = larger;
    }
    
    /** 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < count; i++) {
            sb.append(heap[i]);
            if (i < count - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    */
}