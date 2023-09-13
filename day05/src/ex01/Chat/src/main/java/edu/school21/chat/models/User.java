package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private Long id;

    private String login;

    private String password;

    private List<Chatroom> createdChats = new ArrayList<>();

    private List<Chatroom> socialChats = new ArrayList<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chatroom> getCreatedChats() {
        return createdChats;
    }

    public void setCreatedChats(List<Chatroom> createdChats) {
        this.createdChats = createdChats;
    }

    public List<Chatroom> getSocialChats() {
        return socialChats;
    }

    public void setSocialChats(List<Chatroom> socialChats) {
        this.socialChats = socialChats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(createdChats, user.createdChats) && Objects.equals(socialChats, user.socialChats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdChats, socialChats);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdChats=" + createdChats +
                ", socialChats=" + socialChats +
                '}';
    }
}
