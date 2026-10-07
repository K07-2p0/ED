package Queues_Stacks;

import Exceptions.EmptyCollectionException;

/**
 * Interface que define as operações de uma pilha (Stack)
 */
public interface StackADT<T> {
    
    /**
     * Adiciona um elemento ao topo da pilha
     */
    void push(T element);
    
    /**
     * Remove e retorna o elemento do topo
     */
    T pop() throws EmptyCollectionException;
    
    /**
     * Retorna o elemento do topo sem remover
     */
    T peek() throws EmptyCollectionException;
    
    /**
     * Verifica se a pilha está vazia
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