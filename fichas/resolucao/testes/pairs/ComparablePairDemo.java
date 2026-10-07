package testes.pairs;


// Teste do Ex. 2
public class ComparablePairDemo {
    public static void main(String[] args) {

        ComparablePair<Integer> numeros = new ComparablePair<Integer>(42, 24);
        System.out.println("max(42, 24) = " + numeros.max());

        ComparablePair<Integer> numeros2 = new ComparablePair<Integer>(24, 42);
        System.out.println("max(24, 42) = " + numeros2.max());

        // Strings comparam por ordem alfabetica
        ComparablePair<String> palavras = new ComparablePair<String>("beer", "peanuts");
        System.out.println("max(beer, peanuts) = " + palavras.max());

        ComparablePair<Double> iguais = new ComparablePair<Double>(3.5, 3.5);
        System.out.println("max(3.5, 3.5) = " + iguais.max());

        // Erro de compilacao: Object nao e Comparable
        // ComparablePair<Object> erro = new ComparablePair<Object>(new Object(), new Object());
    }
}
