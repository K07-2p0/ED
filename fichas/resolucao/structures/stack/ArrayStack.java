package structures.stack;

import structures.common.EmptyCollectionException;

// Ex. 1 - stack implementada com um array (slides 16 a 26)
public class ArrayStack<T> implements StackADT<T> {

    // Capacidade inicial por defeito
    private final int DEFAULT_CAPACITY = 100;

    // Número de elementos e também a próxima posição livre do array
    private int top;

    // Array que guarda os elementos (o fundo da stack está no índice 0)
    private T[] stack;

    // Não se pode fazer new T[], por isso cria-se um Object[] e faz-se cast
    public ArrayStack() {
        top = 0;
        stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public ArrayStack(int initialCapacity) {
        top = 0;
        stack = (T[]) (new Object[initialCapacity]);
    }

    // Adiciona ao topo; se o array estiver cheio, aumenta a capacidade
    public void push(T element) {
        if (size() == stack.length) {
            expandCapacity();
        }
        stack[top] = element;
        top++;
    }

    // Remove e devolve o elemento do topo
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }
        top--;
        T result = stack[top];
        stack[top] = null;
        return result;
    }

    // Devolve o elemento do topo sem o remover
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }
        return stack[top - 1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }

    // Elementos do topo para o fundo
    public String toString() {
        String result = "[";
        for (int i = top - 1; i >= 0; i--) {
            result += stack[i];
            if (i > 0) {
                result += ", ";
            }
        }
        return result + "]";
    }

    // Cria um array com o dobro da capacidade e copia os elementos
    private void expandCapacity() {
        T[] larger = (T[]) (new Object[stack.length * 2]);
        for (int i = 0; i < stack.length; i++) {
            larger[i] = stack[i];
        }
        stack = larger;
    }
}
