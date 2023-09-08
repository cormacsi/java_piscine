package ex05;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
    }

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
