package ex03;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
