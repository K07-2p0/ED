package Exceptions;

/**
 * Exceção lançada quando um elemento não é encontrado na estrutura
 */
public class ElementNotFoundException extends Exception {
    
    public ElementNotFoundException(String message) {
        super(message);
    }
    
    public ElementNotFoundException() {
        super("Elemento não encontrado");
    }
} 
    
