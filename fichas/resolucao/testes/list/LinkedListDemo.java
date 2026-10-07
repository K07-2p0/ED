package testes.list;

import structures.list.LinkedList;

// Teste do Ex. 1
public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<String> lista = new LinkedList<String>();

        // O add insere na cabeça, por isso a lista fica pela ordem inversa
        lista.add("A");
        lista.add("B");
        lista.add("C");
        lista.add("D");
        System.out.print("Depois de adicionar A, B, C, D: ");
        lista.print();

        // Remover a cabeça (caso especial)
        lista.remove("D");
        System.out.print("Remover D (cabeca): ");
        lista.print();

        // Remover do meio
        lista.remove("B");
        System.out.print("Remover B (meio): ");
        lista.print();

        // Remover algo que não existe
        System.out.println("Remover X (nao existe): " + lista.remove("X"));

        lista.remove("C");
        lista.remove("A");
        System.out.print("Depois de remover tudo: ");
        lista.print();
        System.out.println("Esta vazia? " + lista.isEmpty());
    }
}
