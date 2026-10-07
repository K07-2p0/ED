package Queues_Stacks;

import Exceptions.EmptyCollectionException;

/**
 * Interface que define as operações de uma fila (Queue)
 */
public interface QueueADT<T> {
    
    /**
     * Adiciona um elemento ao final da fila
     */
    void enqueue(T element);
    
    /**
     * Remove e retorna o elemento da frente
     */
    T dequeue() throws EmptyCollectionException;
    
    /**
     * Retorna o elemento da frente sem remover
     */
    T first() throws EmptyCollectionException;
    
    /**
     * Verifica se a fila está vazia
     */
    boolean isEmpty();
    
    /**
     * Retorna o número de elementos
     */
    int size();
    
    /**
     * Testes
     */
    // String toString();
}