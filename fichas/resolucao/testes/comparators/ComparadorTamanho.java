package testes.comparators;

import java.util.Comparator;

// Parte II Ex. 4 - compara duas Strings pelo tamanho
public class ComparadorTamanho implements Comparator<String> {

    // Negativo se s1 for mais curta, 0 se forem iguais, positivo se for mais comprida
    public int compare(String s1, String s2) {
        return Integer.compare(s1.length(), s2.length());
    }
}
