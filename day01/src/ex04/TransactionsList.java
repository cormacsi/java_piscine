package ex04;

import java.util.UUID;

public interface TransactionsList {
    void addTransaction(Transaction transaction);
    Transaction removeTransaction(UUID id);
    Transaction[] toArray();
    public Transaction getById(UUID id);
}
