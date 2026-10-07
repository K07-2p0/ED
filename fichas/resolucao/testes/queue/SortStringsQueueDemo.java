package testes.queue;

import structures.queue.LinkedQueue;
import structures.queue.QueueADT;

// Parte II, Ex. 5 - ordena n strings juntando queues duas a duas
public class SortStringsQueueDemo {

    public static void main(String[] args) {
        String[] words = { "pera", "maca", "uva", "banana", "kiwi", "laranja", "figo" };

        // Uma queue por cada string
        // e uma queue de queues com todas elas
        QueueADT<QueueADT<String>> queues = new LinkedQueue<QueueADT<String>>();
        for (int i = 0; i < words.length; i++) {
            QueueADT<String> q = new LinkedQueue<String>();
            q.enqueue(words[i]);
            queues.enqueue(q);
        }
        System.out.println("Inicio: " + queues);

        // Junta as duas primeiras queues (de forma ordenada) e reinsere a nova no final,
        // até sobrar só uma queue
        while (queues.size() > 1) {
            QueueADT<String> a = queues.dequeue();
            QueueADT<String> b = queues.dequeue();
            queues.enqueue(MergeSortedQueues.merge(a, b));
            System.out.println("Queues:  " + queues);
        }

        System.out.println("Resultado: " + queues.first());
    }
}
