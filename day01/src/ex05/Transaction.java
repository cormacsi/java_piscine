package ex05;

import java.util.Objects;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private User recipient;
    private User sender;
    private Category transferCategory;
    private Integer transferAmount;

    public Transaction(User sender, User recipient, Integer transferAmount) {
        this.id = UUID.randomUUID();
        this.sender = sender;
        this.recipient = recipient;
        this.transferAmount = transferAmount;
        setTransferCategory();
    }

    public Transaction(UUID id, User sender, User recipient, Integer transferAmount) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.transferAmount = transferAmount;
        setTransferCategory();
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setTransferCategory() {
        if (transferAmount < 0) this.transferCategory = Category.CREDIT;
        else if (transferAmount > 0) this.transferCategory = Category.DEBIT;
        else throw new IllegalTransactionException("The transfer amount is not valid");
    }

    public void setTransferAmount(Integer transferAmount) {
        this.transferAmount = transferAmount;
    }

    public UUID getId() {
        return id;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Category getTransferCategory() {
        return transferCategory;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id) && recipient.equals(that.recipient) &&
                sender.equals(that.sender) && transferCategory == that.transferCategory &&
                Objects.equals(transferAmount, that.transferAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipient, sender, transferCategory, transferAmount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "identifier=" + id +
                ", sender=" + sender.getName() +
                ", recipient=" + recipient.getName() +
                ", transferCategory=" + transferCategory +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
