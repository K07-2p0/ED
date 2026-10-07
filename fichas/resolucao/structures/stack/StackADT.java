package structures.stack;

// Interface StackADT (slides 8 e 9): as operações que qualquer stack tem de ter
public interface StackADT<T> {

    // Adiciona um elemento ao topo
    public void push(T element);

    // Remove e devolve o elemento do topo
    public T pop();

    // Devolve o elemento do topo sem o remover
    public T peek();

    // Indica se a stack está vazia
    public boolean isEmpty();

    // Número de elementos
    public int size();

    // Representação em String
    @Override
    public String toString();
}
