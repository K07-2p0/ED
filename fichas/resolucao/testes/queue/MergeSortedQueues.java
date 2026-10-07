package testes.queue;

import structures.queue.LinkedQueue;
import structures.queue.QueueADT;

// Parte II, Ex. 3 - junta duas queues ordenadas numa terceira também ordenada
public class MergeSortedQueues {

    // Compara os elementos da frente das duas queues e passa o menor para a queue
    // resultado; quando uma das queues esgota, passa o que sobra da outra.
    // As duas queues recebidas ficam vazias (os elementos são removidos com dequeue).
    public static <T extends Comparable<T>> QueueADT<T> merge(QueueADT<T> a, QueueADT<T> b) {
        QueueADT<T> result = new LinkedQueue<T>();

        while (!a.isEmpty() && !b.isEmpty()) {
            if (a.first().compareTo(b.first()) <= 0) {
                result.enqueue(a.dequeue());
            } else {
                result.enqueue(b.dequeue());
            }
        }
        while (!a.isEmpty()) {
            result.enqueue(a.dequeue());
        }
        while (!b.isEmpty()) {
            result.enqueue(b.dequeue());
        }
        return result;
    }

    public static void main(String[] args) {
        QueueADT<Integer> a = new LinkedQueue<Integer>();
        a.enqueue(1);
        a.enqueue(4);
        a.enqueue(7);
        a.enqueue(10);

        QueueADT<Integer> b = new LinkedQueue<Integer>();
        b.enqueue(2);
        b.enqueue(3);
        b.enqueue(8);
        b.enqueue(11);
        b.enqueue(15);

        System.out.println("Queue A:   " + a);
        System.out.println("Queue B:   " + b);
        QueueADT<Integer> merged = merge(a, b);
        System.out.println("Resultado: " + merged);

        // Caso em que uma das queues está vazia
        QueueADT<Integer> empty = new LinkedQueue<Integer>();
        System.out.println("Resultado com uma queue vazia: " + merge(empty, merged));
    }
}
