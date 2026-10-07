package structures.list;

// Ex. 2 - lista ligada com nós sentinela no início e no fim
public class SentinelLinkedList<T> {
    private Node<T> head;   // sentinela do início (não guarda elementos)
    private Node<T> tail;   // sentinela do fim (não guarda elementos)
    private int count;

    // A lista vazia já tem as duas sentinelas ligadas uma à outra
    public SentinelLinkedList() {
        head = new Node<T>(null);
        tail = new Node<T>(null);
        head.setNext(tail);
        count = 0;
    }

    // Adiciona logo a seguir à sentinela do início
    public void add(T element) {
        Node<T> node = new Node<T>(element);
        node.setNext(head.getNext());
        head.setNext(node);
        count++;
    }

    // Remove a primeira ocorrência do elemento; retorna true se removeu
    // Não há casos especiais: há sempre um nó antes (previous) de qualquer elemento
    public boolean remove(T element) {
        Node<T> previous = head;
        Node<T> current = head.getNext();
        while (current != tail && !current.getElement().equals(element)) {
            previous = current;
            current = current.getNext();
        }

        if (current == tail) {
            return false;
        }

        previous.setNext(current.getNext());
        count--;
        return true;
    }

    public int size() {
        return count;
    }

    // Vazia quando a sentinela do início aponta logo para a do fim
    public boolean isEmpty() {
        return head.getNext() == tail;
    }

    // Imprime todos os elementos (as sentinelas não aparecem)
    public void print() {
        System.out.println(toString());
    }

    public String toString() {
        String result = "[";
        Node<T> current = head.getNext();
        while (current != tail) {
            result += current.getElement();
            if (current.getNext() != tail) {
                result += ", ";
            }
            current = current.getNext();
        }
        return result + "]";
    }
}
