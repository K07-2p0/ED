package Lists;

/**
 * Representa um nó com ligações duplas (anterior e próximo)
 */
public class DoubleNode<T> {
    private T element;
    private DoubleNode<T> next;
    private DoubleNode<T> previous;
    
    /**
     * Cria um nó vazio
     */
    public DoubleNode() {
        this.element = null;
        this.next = null;
        this.previous = null;
    }
    
    /**
     * Cria um nó com o elemento especificado
     */
    public DoubleNode(T element) {
        this.element = element;
        this.next = null;
        this.previous = null;
    }
    
    // Getters e Setters
    public T getElement() {
        return element;
    }
    
    public void setElement(T element) {
        this.element = element;
    }
    
    public DoubleNode<T> getNext() {
        return next;
    }
    
    public void setNext(DoubleNode<T> next) {
        this.next = next;
    }
    
    public DoubleNode<T> getPrevious() {
        return previous;
    }
    
    public void setPrevious(DoubleNode<T> previous) {
        this.previous = previous;
    }
}