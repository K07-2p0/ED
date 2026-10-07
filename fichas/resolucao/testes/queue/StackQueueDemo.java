package testes.queue;

import structures.common.EmptyCollectionException;
import structures.queue.QueueADT;
import structures.queue.StackQueue;

// Parte II, Ex. 4 - demonstração da StackQueue (mesmo cenário da Parte I)
public class StackQueueDemo {
    public static void main(String[] args) {
        QueueADT<String> queue = new StackQueue<String>();

        // Cenário: fila de espera de um supermercado
        queue.enqueue("Ana");
        queue.enqueue("Bruno");
        queue.enqueue("Carla");
        System.out.println("Chegaram 3 clientes: " + queue + ", clientes = " + queue.size());
        System.out.println("Proximo a ser atendido (first): " + queue.first());

        System.out.println("Atendido: " + queue.dequeue());
        System.out.println("Atendido: " + queue.dequeue());
        System.out.println("Fila: " + queue);

        queue.enqueue("Diana");
        queue.enqueue("Eduardo");
        queue.enqueue("Filipe");
        System.out.println("Chegaram mais 3 clientes: " + queue + ", clientes = " + queue.size());

        while (!queue.isEmpty()) {
            System.out.println("Atendido: " + queue.dequeue());
        }
        System.out.println("Esta vazia? " + queue.isEmpty());

        // Atender sem clientes na fila
        try {
            queue.dequeue();
        } catch (EmptyCollectionException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
