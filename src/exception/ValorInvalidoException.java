package exception;

public class ValorInvalidoException extends RuntimeException {
    public ValorInvalidoException(String message) {
        super(message);
    }

    public ValorInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
