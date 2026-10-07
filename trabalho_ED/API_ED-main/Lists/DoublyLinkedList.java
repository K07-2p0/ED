package Lists;

import Exceptions.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de uma lista duplamente ligada
 */
public class DoublyLinkedList<T> implements ListADT<T> {
    protected DoubleNode<T> head;
    protected DoubleNode<T> tail;
    protected int count;
    
    /**
     * Cria uma lista vazia
     */
    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }
    
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista vazia");
        }
        
        T element = head.getElement();
        head = head.getNext();
        
        if (head == null) {
            tail = null;
        } else {
            head.setPrevious(null);
        }
        
        count--;
        return element;
    }
    
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista vazia");
        }
        
        T element = tail.getElement();
        tail = tail.getPrevious();
        
        if (tail == null) {
            head = null;
        } else {
            tail.setNext(null);
        }
        
        count--;
        return element;
    }
    
    @Override
    public T remove(T element) throws ElementNotFoundException, EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista vazia");
        }
        
        DoubleNode<T> current = head;
        
        while (current != null && !current.getElement().equals(element)) {
            current = current.getNext();
        }
        
        if (current == null) {
            throw new ElementNotFoundException("Elemento não encontrado");
        }
        
        if (current == head) {
            return removeFirst();
        }
        
        if (current == tail) {
            return removeLast();
        }
        
        current.getPrevious().setNext(current.getNext());
        current.getNext().setPrevious(current.getPrevious());
        count--;
        
        return current.getElement();
    }
    
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista vazia");
        }
        return head.getElement();
    }
    
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Lista vazia");
        }
        return tail.getElement();
    }
    
    @Override
    public boolean contains(T target) {
        DoubleNode<T> current = head;
        
        while (current != null) {
            if (current.getElement().equals(target)) {
                return true;
            }
            current = current.getNext();
        }
        
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return count == 0;
    }
    
    @Override
    public int size() {
        return count;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new DoublyLinkedListIterator();
    }
    
    private class DoublyLinkedListIterator implements Iterator<T> {
        private DoubleNode<T> current = head;
        
        @Override
        public boolean hasNext() {
            return current != null;
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T element = current.getElement();
            current = current.getNext();
            return element;
        }
    }
    
    /*
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        DoubleNode<T> current = head;
        
        while (current != null) {
            sb.append(current.getElement());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }
        
        sb.append("]");
        return sb.toString();
    } */

    // Adiciona estes métodos dentro da classe DoublyLinkedList<T>
public void addLast(T element) {
    DoubleNode<T> newNode = new DoubleNode<>(element);
    if (isEmpty()) {
        head = newNode;
        tail = newNode;
    } else {
        tail.setNext(newNode);
        newNode.setPrevious(tail);
        tail = newNode;
    }
    count++;
}

public void addFirst(T element) {
    DoubleNode<T> newNode = new DoubleNode<>(element);
    if (isEmpty()) {
        head = newNode;
        tail = newNode;
    } else {
        newNode.setNext(head);
        head.setPrevious(newNode);
        head = newNode;
    }
    count++;
}
}