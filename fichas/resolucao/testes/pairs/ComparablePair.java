package testes.pairs;

// Ex. 2 - Pair do slide 32 (com outro nome porque o Pair já existe)
public class ComparablePair<T extends Comparable<T>> {
    private T first;
    private T second;

    public ComparablePair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    // Retorna o maior dos dois elementos
    public T max() {
        if (first.compareTo(second) >= 0)
            return first;
        else
            return second;
    }
}
