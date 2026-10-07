package Lists;

import Exceptions.*;
import java.util.Iterator;

/**
 * Interface que define as operações básicas de uma lista
 */
public interface ListADT<T> extends Iterable<T> {
    
    /**
     * Remove e retorna o primeiro elemento da lista
     */
    T removeFirst() throws EmptyCollectionException;
    
    /**
     * Remove e retorna o último elemento da lista
     */
    T removeLast() throws EmptyCollectionException;
    
    /**
     * Remove e retorna o elemento especificado
     */
    T remove(T element) throws ElementNotFoundException, EmptyCollectionException;
    
    /**
     * Retorna o primeiro elemento sem o remover
     */
    T first() throws EmptyCollectionException;
    
    /**
     * Retorna o último elemento sem o remover
     */
    T last() throws EmptyCollectionException;
    
    /**
     * Verifica se a lista contém o elemento especificado
     */
    boolean contains(T target);
    
    /**
     * Verifica se a lista está vazia
     */
    boolean isEmpty();
    
    /**
     * Retorna o número de elementos na lista
     */
    int size();
    
    /**
     * Retorna um iterador para os elementos da lista
     */
    Iterator<T> iterator();
    
    /**
     * Testes
     */
    //String toString();
}