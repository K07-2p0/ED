package Exceptions;

/**
 * Exceção lançada quando se tenta aceder a uma coleção vazia
 */
public class EmptyCollectionException extends Exception {
    
    public EmptyCollectionException(String message) {
        super(message);
    }
    
    public EmptyCollectionException() {
        super("A coleção está vazia");
    }
}