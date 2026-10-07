package testes.stack;

import structures.common.EmptyCollectionException;
import structures.stack.LinkedStack;

// Teste dos Ex. 3 e 4
public class LinkedStackDemo {
    public static void main(String[] args) {

        // Ex. 3 - comportamento do push e do pop (igual aos slides 33 a 35)
        LinkedStack<String> letras = new LinkedStack<String>();
        letras.push("A");
        letras.push("B");
        letras.push("C");
        letras.push("D");
        System.out.println("Stack inicial (topo -> fundo): " + letras + ", count = " + letras.size());

        letras.push("E");
        System.out.println("push(E): " + letras + ", count = " + letras.size());

        System.out.println("pop(): " + letras.pop());
        System.out.println("Depois do pop: " + letras + ", count = " + letras.size());

        // Ex. 4 - cenário: botão "voltar" de um browser
        System.out.println();
        LinkedStack<String> paginas = new LinkedStack<String>();
        paginas.push("google.com");
        paginas.push("estg.ipp.pt");
        paginas.push("moodle.estg.ipp.pt");
        System.out.println("Pagina atual (peek): " + paginas.peek());
        System.out.println("Voltar: sai " + paginas.pop() + ", fica " + paginas.peek());
        System.out.println("Voltar: sai " + paginas.pop() + ", fica " + paginas.peek());
        System.out.println("Paginas no historico: " + paginas.size());
        paginas.pop();
        System.out.println("Esta vazia? " + paginas.isEmpty());

        // Voltar sem páginas no histórico
        try {
            paginas.peek();
        } catch (EmptyCollectionException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
