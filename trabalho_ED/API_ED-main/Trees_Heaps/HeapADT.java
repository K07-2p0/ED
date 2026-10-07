package Trees_Heaps;

import Exceptions.EmptyCollectionException;

/**
 * Interface que define as operações de um heap
 */
public interface HeapADT<T> {
    
    /**
     * Adiciona o elemento especificado ao heap
     */
    void addElement(T element);
    
    /**
     * Remove e retorna o elemento mínimo/máximo
     */
    T removeMin() throws EmptyCollectionException;
    
    /**
     * Retorna o elemento mínimo/máximo sem remover
     */
    T findMin() throws EmptyCollectionException;
    
    /**
     * Verifica se o heap está vazio
     */
    boolean isEmpty();
    
    /**
     * Retorna o número de elementos
     */
    int size();
    
    /**
     * Testes
     */
    //String toString();
}