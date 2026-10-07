package testes.stack;

import structures.stack.PostfixCalculator;
import structures.stack.PostfixCalculatorLinked;

// Teste dos Ex. 1 e 2 (Parte II): as duas calculadoras têm de dar o mesmo resultado
public class PostfixCalculatorDemo {
    public static void main(String[] args) {
        PostfixCalculator comArray = new PostfixCalculator();
        PostfixCalculatorLinked comLinked = new PostfixCalculatorLinked();

        // Exemplos dos slides 12 e 14
        String[] expressoes = {
            "3 4 + 2 *",
            "1 2 + 4 * 3 +",
            "7 4 -3 * 1 5 + / *"
        };

        for (String expressao : expressoes) {
            System.out.println(expressao + "  =  " + comArray.evaluate(expressao)
                + " (ArrayStack)  /  " + comLinked.evaluate(expressao) + " (LinkedStack)");
        }
    }
}
