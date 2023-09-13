package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.*;

import java.util.Optional;

public class Program {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    private static final String USERNAME = "user";

    private static final String PASSWORD = "admin";

    private static HikariDataSource data;

    public static void main(String[] args) {
        createConnection();
        System.out.println("Update a message in DataBase: ");
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(data);
        Optional<Message> messageOptional = messagesRepository.findById(11L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye");
            message.setDate(null);
            messagesRepository.update(message);
            System.out.println("Message: ");
            System.out.println(message);
        }
    }

    private static void createConnection() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        data = new HikariDataSource(config);
    }
}
