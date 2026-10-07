package testes.queue;

import structures.common.EmptyCollectionException;
import structures.queue.LinkedQueue;

// Ex. 1 - demonstração da LinkedQueue
public class LinkedQueueDemo {
    public static void main(String[] args) {
        LinkedQueue<String> fila = new LinkedQueue<String>();

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
