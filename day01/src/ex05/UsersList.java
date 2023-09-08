package ex05;

public interface UsersList {
    void addUser(User user);
    User getById(Integer id);
    User getByIndex(int index);
    int getSize();
}
