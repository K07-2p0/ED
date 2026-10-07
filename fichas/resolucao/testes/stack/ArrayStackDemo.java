package testes.stack;

import structures.stack.ArrayStack;
import structures.common.EmptyCollectionException;

// Teste do Ex. 1 - cenário: "desfazer" (undo) num editor de texto
public class ArrayStackDemo {
    public static void main(String[] args) {
        // Capacidade 2 de propósito, para se ver o expandCapacity a funcionar
        ArrayStack<String> historico = new ArrayStack<String>(2);

        historico.push("Escrever 'Ola'");
        historico.push("Escrever ' mundo'");
        historico.push("Apagar 'mundo'");
        historico.push("Colocar a negrito");
        System.out.println("Historico (topo -> fundo): " + historico);
        System.out.println("Acoes guardadas: " + historico.size());

        // A ultima ação feita é a primeira a ser desfeita (LIFO)
        System.out.println("Proxima a desfazer (peek): " + historico.peek());
        System.out.println("Desfazer: " + historico.pop());
        System.out.println("Desfazer: " + historico.pop());
        System.out.println("Historico: " + historico);

        historico.pop();
        historico.pop();
        System.out.println("Esta vazia? " + historico.isEmpty());

        // Desfazer sem nada no histórico
        try {
            historico.pop();
        } catch (EmptyCollectionException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
