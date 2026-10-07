package structures.queue;

import structures.common.EmptyCollectionException;
import structures.stack.LinkedStack;
import structures.stack.StackADT;

// Parte II, Ex. 4 - queue implementada com duas stacks
public class StackQueue<T> implements QueueADT<T> {

    // Guarda os elementos com a frente da queue no topo (o fundo é a traseira)
    private StackADT<T> mainStack;

    // Stack auxiliar, usada só durante o enqueue
    private StackADT<T> auxStack;

    public StackQueue() {
        mainStack = new LinkedStack<T>();
        auxStack = new LinkedStack<T>();
    }

    // O novo elemento tem de ficar no fundo da mainStack. Para isso passam-se todos
    // os elementos para a auxStack (ficam invertidos), coloca-se o novo na mainStack
    // vazia e voltam-se a passar os elementos da auxStack (recuperam a ordem original)
    public void enqueue(T element) {
        while (!mainStack.isEmpty()) {
            auxStack.push(mainStack.pop());
        }
        mainStack.push(element);
        while (!auxStack.isEmpty()) {
            mainStack.push(auxStack.pop());
        }
    }

    // A frente da queue está no topo da mainStack
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue");
        }
        return mainStack.pop();
    }

    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue");
        }
        return mainStack.peek();
    }

    public boolean isEmpty() {
        return mainStack.isEmpty();
    }

    public int size() {
        return mainStack.size();
    }

    // O toString da stack vai do topo para o fundo, ou seja, da frente para a traseira
    public String toString() {
        return mainStack.toString();
    }
}
