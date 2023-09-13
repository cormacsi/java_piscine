package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ChatroomRepositoryJdbcImpl implements ChatroomRepository {

    private final DataSource dataSource;

    private final UserRepository userRepository;

    public ChatroomRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.userRepository = new UserRepositoryJdbcImpl(dataSource);
    }

    @Override
    public Optional<Chatroom> findById(Long id) {
        Chatroom chatroom = null;
        String query = "SELECT id, name, owner_id FROM chatroom WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                Optional<User> user = userRepository.findById(resultSet.getLong("owner_id"));
                if (user.isPresent()) {
                    chatroom = new Chatroom(resultSet.getLong("id"),
                            resultSet.getString("name"), user.get());
                } else {
                    System.err.println("User Optional is null in ChatroomRepository!");
                }
            }
        } catch (SQLException e) {
            System.err.println("An error in using DataBase in ChatroomRepository!");
        }
        return Optional.ofNullable(chatroom);
    }
}
