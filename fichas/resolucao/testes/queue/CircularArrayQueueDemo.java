package testes.queue;

import structures.common.EmptyCollectionException;
import structures.queue.CircularArrayQueue;

// Ex. 2 - demonstração da CircularArrayQueue (mesmo cenário do Ex. 1)
public class CircularArrayQueueDemo {
    public static void main(String[] args) {
        // Capacidade 3 de propósito, para se ver o array a dar a volta e o expandCapacity a funcionar
        CircularArrayQueue<String> fila = new CircularArrayQueue<String>(3);

        // Cenário: fila de espera de um supermercado
        fila.enqueue("Ana");
        fila.enqueue("Bruno");
        fila.enqueue("Carla");
        System.out.println("Chegaram 3 clientes: " + fila + ", clientes = " + fila.size());
        System.out.println("Proximo a ser atendido (first): " + fila.first());

        System.out.println("Atendido: " + fila.dequeue());
        System.out.println("Atendido: " + fila.dequeue());
        System.out.println("Fila: " + fila);

        fila.enqueue("Diana");
        fila.enqueue("Eduardo");
        fila.enqueue("Filipe");
        System.out.println("Chegaram mais 3 clientes: " + fila + ", clientes = " + fila.size());

        while (!fila.isEmpty()) {
            System.out.println("Atendido: " + fila.dequeue());
        }
        System.out.println("Esta vazia? " + fila.isEmpty());

        // Atender sem clientes na fila
        try {
            fila.dequeue();
        } catch (EmptyCollectionException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
