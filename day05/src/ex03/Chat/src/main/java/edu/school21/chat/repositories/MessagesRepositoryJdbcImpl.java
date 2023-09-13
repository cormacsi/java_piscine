package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
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
                    Timestamp date = resultSet.getTimestamp("date");
                    if (date == null) {
                        message = new Message(resultSet.getLong("id"),
                                user.get(),
                                chatroom.get(),
                                resultSet.getString("text"), null);
                    } else {
                        message = new Message(resultSet.getLong("id"),
                                user.get(),
                                chatroom.get(),
                                resultSet.getString("text"),
                                resultSet.getTimestamp("date").toLocalDateTime());
                    }
                } else {
                    System.err.println("User or Chatroom Optional is null in MessageRepository!");
                }
            }
        } catch (SQLException e) {
            System.err.println("An error in FINDING by id in MessageRepository!");
        }
        return Optional.ofNullable(message);
    }

    @Override
    public void save(Message message) throws NotSavedSubEntityException {
        Long authorId = message.getAuthor().getId();
        Long chatroomId = message.getChatroom().getId();
        Optional<User> author = userRepository.findById(authorId);
        Optional<Chatroom> chatroom = chatroomRepository.findById(chatroomId);
        if (!author.isPresent()) {
            throw new NotSavedSubEntityException("Author is not found!");
        } else if (!chatroom.isPresent()) {
            throw new NotSavedSubEntityException("Chatroom is not found!");
        }
        String query = "INSERT INTO message (author_id, chatroom_id, text, date) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, authorId);
            statement.setLong(2, chatroomId);
            statement.setString(3, message.getText());
            statement.setTimestamp(4, Timestamp.valueOf(message.getDate()));
            statement.execute();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                message.setId(keys.getLong(1));
            } else {
                System.err.println("The message id was not returned!");
            }
        } catch (SQLException e) {
            System.err.println("An error in SAVING in MessageRepository!");
        }
    }

    @Override
    public void update(Message message) {
        Long authorId = message.getAuthor().getId();
        Long chatroomId = message.getChatroom().getId();
        Optional<User> author = userRepository.findById(authorId);
        Optional<Chatroom> chatroom = chatroomRepository.findById(chatroomId);
        if (!author.isPresent()) {
            throw new NotSavedSubEntityException("Author is not found!");
        } else if (!chatroom.isPresent()) {
            throw new NotSavedSubEntityException("Chatroom is not found!");
        }
        String query = "UPDATE message SET author_id = ?, chatroom_id = ?, text = ?, date = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, authorId);
            statement.setLong(2, chatroomId);
            statement.setString(3, message.getText());
            if (message.getDate() == null) {
                statement.setNull(4, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(4, Timestamp.valueOf(message.getDate()));
            }
            statement.setLong(5, message.getId());
            statement.execute();
        } catch (SQLException e) {
            System.err.println("An error in UPDATING in MessageRepository!");
        }
    }
}
