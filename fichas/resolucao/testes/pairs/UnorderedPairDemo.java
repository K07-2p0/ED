package testes.pairs;


// Teste do Ex. 3
public class UnorderedPairDemo {
    public static void main(String[] args) {
        UnorderedPair<String> p1 =
            new UnorderedPair<String>("peanuts", "beer");
        UnorderedPair<String> p2 =
            new UnorderedPair<String>("beer", "peanuts");

        if (p1.equals(p2)) {
            System.out.println(p1.getFirst() + " and "
                + p1.getSecond() + " is the same as");
            System.out.println(p2.getFirst() + " and "
                + p2.getSecond());
        }

        // Teste dos getters
        System.out.println();
        System.out.println("Primeiro elemento de p1: " + p1.getFirst());
        System.out.println("Segundo elemento de p1: " + p1.getSecond());

        // Teste do isEquals
        UnorderedPair<String> p3 =
            new UnorderedPair<String>("beer", "beer");
        System.out.println("p1 tem elementos iguais? " + p1.isEquals());
        System.out.println("p3 tem elementos iguais? " + p3.isEquals());
    }
}
