package structures.list;

// Ex. 1 - lista ligada simples (não confundir com java.util.LinkedList)
public class LinkedList<T> {
    private Node<T> head;
    private int count;

    public LinkedList() {
        head = null;
        count = 0;
    }

    // Adiciona na cabeça da lista (slide 11)
    public void add(T element) {
        Node<T> node = new Node<T>(element);
        node.setNext(head);
        head = node;
        count++;
    }

    // Remove a primeira ocorrência do elemento; retorna true se removeu
    public boolean remove(T element) {
        // Caso especial: lista vazia
        if (head == null) {
            return false;
        }

        // Caso especial: o elemento está na cabeça (slide 13)
        if (head.getElement().equals(element)) {
            head = head.getNext();
            count--;
            return true;
        }

        // Caso geral: procurar com previous/current (slide 14)
        Node<T> previous = head;
        Node<T> current = head.getNext();
        while (current != null && !current.getElement().equals(element)) {
            previous = current;
            current = current.getNext();
        }

        if (current == null) {
            return false;
        }

        previous.setNext(current.getNext());
        count--;
        return true;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    // Imprime todos os elementos (para verificar a integridade da lista)
    public void print() {
        System.out.println(toString());
    }

    public String toString() {
        String result = "[";
        Node<T> current = head;
        while (current != null) {
            result += current.getElement();
            if (current.getNext() != null) {
                result += ", ";
            }
            current = current.getNext();
        }
        return result + "]";
    }
}
