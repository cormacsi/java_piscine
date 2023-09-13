package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class Program {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    private static final String USERNAME = "user";

    private static final String PASSWORD = "admin";

    private static HikariDataSource data;

    public static void main(String[] args) {
        createConnection();
        System.out.println("Enter a message ID");
        String messageId;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            messageId = reader.readLine();
            parseMessage(messageId);
        } catch (IOException e) {
            System.err.println("IOException in Program main!");
        }
        data.close();
    }

    private static void parseMessage(String messageId) {
        if (messageId.matches("^\\d+$")) {
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(data);
            Optional<Message> message = messagesRepository.findById(Long.parseLong(messageId));
            if (message.isPresent()) {
                System.out.println(message.get());
            } else {
                System.out.println("Message does not exist!");
            }
        } else {
            System.err.println("The argument is not valid!");
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
