package structures.list;

// Nó de uma lista simplesmente ligada: guarda um elemento e a ligação para o seguinte
public class Node<T> {
    private T element;
    private Node<T> next;

    public Node(T element) {
        this.element = element;
        this.next = null;
    }

    public T getElement() {
        return element;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }
}
