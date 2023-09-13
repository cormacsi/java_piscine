package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private final DataSource dataSource;

    private final UserRepository userRepository;

    private final ChatroomRepository chatroomRepository;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.userRepository = new UserRepositoryJdbcImpl(dataSource);
        this.chatroomRepository = new ChatroomRepositoryJdbcImpl(dataSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        Message message = null;
        String query = "SELECT id, author_id, chatroom_id, text, date FROM message WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                Optional<User> user = userRepository.findById(resultSet.getLong("author_id"));
                Optional<Chatroom> chatroom = chatroomRepository.findById(resultSet.getLong("chatroom_id"));
                if (user.isPresent() && chatroom.isPresent()) {
                    message = new Message(resultSet.getLong("id"),
                            user.get(),
                            chatroom.get(),
                            resultSet.getString("text"),
                            resultSet.getTimestamp("date").toLocalDateTime());
                } else {
                    System.err.println("User or Chatroom Optional is null in MessageRepository!");
                }
            }
        } catch (SQLException e) {
            System.err.println("An error in using DataBase in MessageRepository!");
        }
        return Optional.ofNullable(message);
    }
}
