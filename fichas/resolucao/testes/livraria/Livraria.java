package testes.livraria;

import java.util.ArrayList;
import java.util.List;

// Parte II Ex. 5 - livraria genérica: T só pode ser Media ou uma subclasse (Livro, Video, CD)
public class Livraria<T extends Media> {
    private List<T> itens;

    public Livraria() {
        itens = new ArrayList<T>();
    }

    // Armazena uma média
    public void adicionar(T item) {
        itens.add(item);
    }

    // Obtém a média numa posição
    public T obter(int posicao) {
        return itens.get(posicao);
    }

    // Obtém a primeira média com este título (null se não existir)
    public T obterPorTitulo(String titulo) {
        for (T item : itens) {
            if (item.getTitulo().equalsIgnoreCase(titulo)) {
                return item;
            }
        }
        return null;
    }

    // Remove uma média; retorna true se existia
    public boolean remover(T item) {
        return itens.remove(item);
    }

    // Obtém todas as médias (cópia, para não mexerem na lista interna)
    public List<T> obterTodos() {
        return new ArrayList<T>(itens);
    }

    public int tamanho() {
        return itens.size();
    }

    public boolean estaVazia() {
        return itens.isEmpty();
    }

    // Imprime qualquer livraria (wildcard: Livraria<Livro>, Livraria<CD>, ...)
    public static void imprimir(Livraria<? extends Media> livraria) {
        for (Media m : livraria.itens) {
            System.out.println("  " + m);
        }
    }
}
