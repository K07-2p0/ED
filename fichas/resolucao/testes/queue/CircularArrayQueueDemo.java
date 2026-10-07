package testes.queue;

import structures.common.EmptyCollectionException;
import structures.queue.CircularArrayQueue;

// Ex. 2 - demonstração da CircularArrayQueue (mesmo cenário do Ex. 1)
public class CircularArrayQueueDemo {
    public static void main(String[] args) {
        // Capacidade 3 de propósito, para se ver o array a dar a volta e o expandCapacity a funcionar
        CircularArrayQueue<String> queue = new CircularArrayQueue<String>(3);

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
