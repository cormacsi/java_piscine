package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import java.util.List;

public class Program {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    private static final String USERNAME = "user";

    private static final String PASSWORD = "admin";

    private static HikariDataSource data;

    public static void main(String[] args) {
        createConnection();
        System.out.println("Finding all users by page and size: ");
        UserRepository userRepository = new UserRepositoryJdbcImpl(data);
        List<User> userList = userRepository.findAll(1, 4);
        data.close();
        int i = 1;
        for (User user : userList) {
            System.out.println("-------------------------------------------------------------------");
            System.out.printf("User %d: user_id = %d login = %s password = %s\n", i++, user.getId(), user.getLogin(), user.getPassword());
            System.out.println("Created chatrooms:");
            for (Chatroom chat : user.getCreatedChats()) {
                System.out.printf("Chatroom id = %d name = %s owner = %s(id=%d)\n", chat.getId(), chat.getName(), chat.getOwner().getLogin(), chat.getOwner().getId());
            }
            System.out.println("Social chatrooms:");
            for (Chatroom chat : user.getSocialChats()) {
                System.out.printf("Chatroom id = %d name = %s owner = %s(id=%d)\n", chat.getId(), chat.getName(), chat.getOwner().getLogin(), chat.getOwner().getId());
            }
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
