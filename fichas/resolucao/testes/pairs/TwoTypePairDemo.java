package testes.pairs;


// Teste do Ex. 1
public class TwoTypePairDemo {
    public static void main(String[] args) {

        // Par com dois tipos diferentes
        TwoTypePair<String, Integer> p1 =
            new TwoTypePair<String, Integer>("Idade", 20);

        System.out.println("p1:");
        System.out.println(p1);

        String nome = p1.getFirst();
        Integer valor = p1.getSecond();
        System.out.println("getFirst(): " + nome);
        System.out.println("getSecond(): " + valor);

        p1.setFirst("Ano");
        p1.setSecond(2026);
        System.out.println("Depois dos setters:");
        System.out.println(p1);

        TwoTypePair<String, Integer> p2 =
            new TwoTypePair<String, Integer>("Ano", 2026);
        TwoTypePair<String, Integer> p3 =
            new TwoTypePair<String, Integer>("Ano", 2025);
        System.out.println("p1 igual a p2? " + p1.equals(p2));
        System.out.println("p1 igual a p3? " + p1.equals(p3));

        // A ordem importa
        TwoTypePair<Integer, String> p4 =
            new TwoTypePair<Integer, String>(2026, "Ano");
        System.out.println("p1 igual a p4? " + p1.equals(p4));

        // Erro de compilacao: o primeiro elemento e String
        // p1.setFirst(10);
    }
}
