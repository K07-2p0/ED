package structures.common;

// Nó usado pela LinkedStack e pela LinkedQueue
public class LinearNode<T> {

    // Referência para o nó seguinte
    private LinearNode<T> next;

    // Elemento guardado neste nó
    private T element;

    // Cria um nó vazio
    public LinearNode() {
        next = null;
        element = null;
    }

    // Cria um nó com o elemento
    public LinearNode(T elem) {
        next = null;
        element = elem;
    }

    public LinearNode<T> getNext() {
        return next;
    }

    public void setNext(LinearNode<T> node) {
        next = node;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T elem) {
        element = elem;
    }
}
