package testes.pairs;

public class UnorderedPair<T> extends Pair<T> {

    // Chama os construtores do Pair
    public UnorderedPair() {
        super();
    }

    public UnorderedPair(T firstItem, T secondItem) {
        super(firstItem, secondItem);
    }

    // Retorna o primeiro elemento do par (chama o getFirst() herdado do Pair com super)
    public T getFirst() {
        return super.getFirst();
    }

    // Retorna o segundo elemento do par (chama o getSecond() herdado do Pair com super)
    public T getSecond() {
        return super.getSecond();
    }

    //Metodo que verifica se os elementos do par são iguais
    //"os meus dois elementos são iguais entre si?"
    public boolean isEquals() {
        return getFirst().equals(getSecond());
    }

    //olha para dois pares e pergunta: "este par é igual àquele par, mesmo que a ordem esteja trocada?"
    public boolean equals(Object otherObject) {

        if (otherObject == null) {
            return false;
        } 
        else if (getClass() != otherObject.getClass()) {
            return false;
        } 
        else {
            UnorderedPair<?> otherPair = (UnorderedPair<?>) otherObject;
            return (getFirst().equals(otherPair.getFirst()) && getSecond().equals(otherPair.getSecond()))
            || (getFirst().equals(otherPair.getSecond()) && getSecond().equals(otherPair.getFirst()));
        }
    }

    // hashCode igual para (a,b) e (b,a), como o equals
    public int hashCode() {
        return getFirst().hashCode() + getSecond().hashCode();
    }
}