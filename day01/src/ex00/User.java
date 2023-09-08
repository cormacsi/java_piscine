package ex00;

import java.util.Objects;

public class User {
    private static Integer idGenerator = 0;
    private final Integer id;
    private String name;
    private Integer balance;

    public User(String name, Integer balance) {
        this.id = idGenerator;
        setName(name);
        if (balance >= 0) this.balance = balance;
        else this.balance = 0;
        idGenerator++;
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