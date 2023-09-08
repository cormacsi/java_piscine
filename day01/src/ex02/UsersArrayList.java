package ex02;

import java.util.Arrays;

public class UsersArrayList implements UsersList {
    private int capacity = 10;

    private int size = 0;

    private User[] userList;

    public UsersArrayList() {
        userList = new User[capacity];
    }

    private void addCapacity() {
        capacity = capacity * 2;
        User[] newUserList = new User[capacity];
        if (size >= 0) System.arraycopy(userList, 0, newUserList, 0, size);
        userList = newUserList;
    }

    @Override
    public void addUser(User user) {
        if (size == capacity) addCapacity();
        userList[size++] = user;
    }

    @Override
    public User getById(Integer id) {
        for (int i = 0; i < size; i++) {
            if (id.equals(userList[i].getId()))
                return userList[i];
        }
        throw new UserNotFoundException("No such id!");
    }

    @Override
    public User getByIndex(int index) {
        if (index < 0 || index >= size) throw new UserNotFoundException("No such index");
        return userList[index];
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "UsersArrayList{" +
                "capacity=" + capacity +
                ", size=" + size +
                ", \nuserList=" + Arrays.toString(userList) +
                '}';
    }
}
