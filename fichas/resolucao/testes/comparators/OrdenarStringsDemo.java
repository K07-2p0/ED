package testes.comparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Teste do Ex. 4 (Parte II)
public class OrdenarStringsDemo {
    public static void main(String[] args) {

        // Lista de strings pré-definidas (List<String> -> generics)
        List<String> palavras = new ArrayList<String>();
        palavras.add("estruturas");
        palavras.add("de");
        palavras.add("dados");
        palavras.add("generics");
        palavras.add("java");
        palavras.add("ordenacao");

        System.out.println("Antes: " + palavras);

        // Ordena pelo tamanho usando o comparador
        Collections.sort(palavras, new ComparadorTamanho());

        System.out.println("Depois: " + palavras);
    }
}
