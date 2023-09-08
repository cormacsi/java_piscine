package ex04;

import java.util.Objects;

public class User {
    private final Integer id;
    private String name;
    private Integer balance;

    private TransactionsList transactions;

    public User(String name, Integer balance) {
        this.id = UserIdsGenerator.generateId();
        setName(name);
        if (balance >= 0) this.balance = balance;
        else this.balance = 0;
        transactions = new TransactionsLinkedList();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) this.name = name;
        else this.name = "";
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public TransactionsList getTransactions() {
        return transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && name.equals(user.name) && balance.equals(user.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance);
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}