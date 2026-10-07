package structures.stack;

// Parte II Ex. 1 - calculadora postfix com ArrayStack
public class PostfixCalculator {
    private ArrayStack<Integer> stack;

    public PostfixCalculator() {
        stack = new ArrayStack<Integer>();
    }

    // Avalia uma expressão postfix com os elementos separados por espaços, ex: "3 4 + 2 *"
    public int evaluate(String expression) {
        stack = new ArrayStack<Integer>();
        String[] tokens = expression.trim().split("\\s+");

        for (String token : tokens) {
            if (isOperator(token)) {
                // Operador: tira dois operandos (o segundo está no topo), calcula e guarda o resultado
                int op2 = stack.pop();
                int op1 = stack.pop();
                stack.push(calculate(op1, op2, token));
            } else {
                // Operando: vai para a stack
                stack.push(Integer.parseInt(token));
            }
        }

        // No fim só pode sobrar um valor: o resultado
        int result = stack.pop();
        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("Expressao invalida: sobram operandos");
        }
        return result;
    }

    // "-3" é um número, por isso compara-se a String inteira
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
