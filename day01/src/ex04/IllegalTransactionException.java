package ex04;

public class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException() {
    }

    public IllegalTransactionException(String message) {
        super(message);
    }
}
