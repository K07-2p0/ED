package structures.common;

// Exceção lançada quando se remove/consulta uma coleção vazia
public class EmptyCollectionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmptyCollectionException(String collection) {
        super("A " + collection + " esta vazia");
    }
}
