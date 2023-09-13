package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {

    private Long id;

    private User author;

    private Chatroom chatroom;

    private String text;

    private LocalDateTime date;

    public Message(User author, Chatroom chatroom, String text) {
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        date = LocalDateTime.now();
    }

    public Message(User author, Chatroom chatroom, String text, LocalDateTime date) {
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.date = date;
    }

    public Message(Long id, User author, Chatroom chatroom, String text, LocalDateTime date) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(author, message.author) && Objects.equals(chatroom, message.chatroom) && Objects.equals(text, message.text) && Objects.equals(date, message.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, chatroom, text, date);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ",\nauthor=" + author +
                ",\nchatroom=" + chatroom +
                ",\ntext='" + text + '\'' +
                ",\ndate=" + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }
}
