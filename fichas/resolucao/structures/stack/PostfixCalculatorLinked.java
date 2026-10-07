package structures.stack;

// Parte II Ex. 2 - a mesma calculadora, mas com LinkedStack
public class PostfixCalculatorLinked {
    // Alteração 1: o tipo do atributo passa de ArrayStack para LinkedStack
    private LinkedStack<Integer> stack;

    public PostfixCalculatorLinked() {
        // Alteração 2: instanciar LinkedStack em vez de ArrayStack
        stack = new LinkedStack<Integer>();
    }

    // Avalia uma expressão postfix com os elementos separados por espaços, ex: "3 4 + 2 *"
    public int evaluate(String expression) {
        // Alteração 3: instanciar LinkedStack em vez de ArrayStack
        stack = new LinkedStack<Integer>();
        String[] tokens = expression.trim().split("\\s+");

        // Daqui para baixo fica tudo igual: push, pop e isEmpty são os mesmos (interface StackADT)
        for (String token : tokens) {
            if (isOperator(token)) {
                int op2 = stack.pop();
                int op1 = stack.pop();
                stack.push(calculate(op1, op2, token));
            } else {
                stack.push(Integer.parseInt(token));
            }
        }

        int result = stack.pop();
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("Expressao invalida: sobram operandos");
        }
        return result;
    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private int calculate(int op1, int op2, String operator) {
        switch (operator) {
            case "+":
                return op1 + op2;
            case "-":
                return op1 - op2;
            case "*":
                return op1 * op2;
            default:
                return op1 / op2;
        }
    }
}
