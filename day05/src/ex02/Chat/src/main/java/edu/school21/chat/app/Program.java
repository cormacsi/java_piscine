package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import java.time.LocalDateTime;
import java.util.Optional;

public class Program {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    private static final String USERNAME = "user";

    private static final String PASSWORD = "admin";

    private static HikariDataSource data;

    public static void main(String[] args) {
        createConnection();
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(data);
        System.out.println("Adding a valid message: ");
        UserRepository userRepository = new UserRepositoryJdbcImpl(data);
        ChatroomRepository chatroomRepository = new ChatroomRepositoryJdbcImpl(data);
        Optional<User> author = userRepository.findById(7L);
        Optional<Chatroom> chatroom = chatroomRepository.findById(8L);
        if (author.isPresent() && chatroom.isPresent()) {
            Message message = new Message(null, author.get(), chatroom.get(), "Hello!", LocalDateTime.now());
            try {
                messagesRepository.save(message);
                System.out.println("Message id: " + message.getId());
            } catch (NotSavedSubEntityException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Could not find and parse existing user or chatroom!");
        }

        System.out.println("Adding an invalid message: ");
        User invalidAuthor = new User(0L, "Tom", "password");
        Chatroom invalidChatroom = new Chatroom(0L, "invalidRoom", invalidAuthor);
        Message invalidMessage = new Message(invalidAuthor, invalidChatroom, "Invalid text", LocalDateTime.now());
        try {
            messagesRepository.save(invalidMessage);
            System.out.println("Message id: " + invalidMessage.getId());
        } catch (NotSavedSubEntityException e) {
            System.err.println(e.getMessage());
        }
        data.close();
    }

    private static void createConnection() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        data = new HikariDataSource(config);
    }
}
