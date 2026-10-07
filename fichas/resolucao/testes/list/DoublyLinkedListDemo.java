package testes.list;

import structures.list.DoublyLinkedList;
import java.util.Arrays;

// Teste do Ex. 4 (Parte I) e dos Ex. 2, 3, 4 (Parte II)
public class DoublyLinkedListDemo {
    public static void main(String[] args) {
        DoublyLinkedList<Integer> lista = new DoublyLinkedList<Integer>();

        // Parte I Ex. 4
        System.out.println("Esta vazia? " + lista.isEmpty());
        lista.addFirst(5);
        lista.addFirst(4);
        lista.addFirst(3);
        lista.addFirst(2);
        lista.addFirst(1);
        System.out.print("Depois de inserir 5, 4, 3, 2, 1 na cabeca: ");
        lista.print();

        System.out.println("removeFirst: " + lista.removeFirst());
        System.out.println("removeLast: " + lista.removeLast());
        System.out.print("Lista: ");
        lista.print();
        System.out.println("Esta vazia? " + lista.isEmpty());

        // Parte II Ex. 2 (posições começam em 0)
        DoublyLinkedList<Integer> numeros = new DoublyLinkedList<Integer>();
        for (int i = 10; i <= 16; i++) {
            numeros.addLast(i);
        }
        System.out.println();
        System.out.print("Numeros: ");
        numeros.print();
        System.out.println("toArray(): " + Arrays.toString(numeros.toArray()));
        System.out.println("toArrayUntil(2): " + Arrays.toString(numeros.toArrayUntil(2)));
        System.out.println("toArrayAfter(4): " + Arrays.toString(numeros.toArrayAfter(4)));
        System.out.println("toArrayBetween(1, 3): " + Arrays.toString(numeros.toArrayBetween(1, 3)));

        // Parte II Ex. 3
        System.out.print("Pares: ");
        numeros.getEvenElements().print();

        // Parte II Ex. 4
        DoublyLinkedList<Integer> repetidos = new DoublyLinkedList<Integer>();
        repetidos.addLast(7);
        repetidos.addLast(1);
        repetidos.addLast(7);
        repetidos.addLast(2);
        repetidos.addLast(7);
        System.out.println();
        System.out.print("Repetidos: ");
        repetidos.print();
        System.out.println("Quantos 7? " + repetidos.countOccurrences(7));
        System.out.println("7 removidos: " + repetidos.removeAllOccurrences(7));
        System.out.print("Depois de remover: ");
        repetidos.print();

        // Integridade: remover pela cauda tem de continuar a funcionar
        System.out.println("removeLast: " + repetidos.removeLast());
        System.out.println("removeLast: " + repetidos.removeLast());
        System.out.println("Esta vazia? " + repetidos.isEmpty());
    }
}
