package structures.list;

// Nó de uma lista duplamente ligada: liga ao seguinte e ao anterior
public class DoubleNode<T> {
    private T element;
    private DoubleNode<T> next;
    private DoubleNode<T> previous;

    public DoubleNode(T element) {
        this.element = element;
        this.next = null;
        this.previous = null;
    }

    public T getElement() {
        return element;
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
