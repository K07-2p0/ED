package testes.list;

import structures.list.SentinelLinkedList;

// Teste do Ex. 2 (os mesmos passos do Ex. 1, para comparar)
public class SentinelLinkedListDemo {
    public static void main(String[] args) {
        SentinelLinkedList<String> lista = new SentinelLinkedList<String>();

        lista.add("A");
        lista.add("B");
        lista.add("C");
        lista.add("D");
        System.out.print("Depois de adicionar A, B, C, D: ");
        lista.print();

        lista.remove("D");
        System.out.print("Remover D (primeiro): ");
        lista.print();

        lista.remove("B");
        System.out.print("Remover B (meio): ");
        lista.print();

        System.out.println("Remover X (nao existe): " + lista.remove("X"));

        lista.remove("C");
        lista.remove("A");
        System.out.print("Depois de remover tudo: ");
        lista.print();
        System.out.println("Esta vazia? " + lista.isEmpty());
    }
}
