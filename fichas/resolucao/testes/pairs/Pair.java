package testes.pairs;

public class Pair<T> {
    private T first;
    private T second;
    
    public Pair() {
        first = null;
        second = null;
    }

    public Pair(T firstItem, T secondItem) {
        first = firstItem;
        second = secondItem;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    public String toString() {
        return ("first: " + first.toString() + "\n"
        + "second: " + second.toString());
    }

    public boolean equals(Object otherObject) {
        if (otherObject == null) {
            return false;
        } else if (getClass() != otherObject.getClass()) {
            return false;
        } else {
            Pair<?> otherPair = (Pair<?>) otherObject;
            return (first.equals(otherPair.first)
            && second.equals(otherPair.second));
        }
    }

    // Quem redefine o equals deve redefinir o hashCode
    public int hashCode() {
        return 31 * first.hashCode() + second.hashCode();
    }
}