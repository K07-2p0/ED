package Trees_Heaps;

/**
 * Nó para fila de prioridade (Atualizado para suportar prioridade double)
 */
public class PriorityQueueNode<T> implements Comparable<PriorityQueueNode<T>> {
    private T element;
    private double priority; // Alterado de int para double
    
    public PriorityQueueNode(T element, double priority) {
        this.element = element;
        this.priority = priority;
    }
    
    public T getElement() {
        return element;
    }
    
    public double getPriority() {
        return priority;
    }
    
    @Override
    public int compareTo(PriorityQueueNode<T> other) {
        // Usar Double.compare para lidar corretamente com decimais
        return Double.compare(this.priority, other.priority);
    }
    
    /**
    @Override
    public String toString() {
        return element + " (prioridade: " + priority + ")";
    }
    */
}