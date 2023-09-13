package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        String query = "SELECT id, login, password FROM \"user\" WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                user = new User(resultSet.getLong("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"));
            }
        } catch (SQLException e) {
            System.err.println("An error in FINDING by id in UserRepository!");
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll(int page, int size) {
        List<User> userList = new ArrayList<>();
        String query = "WITH us AS (SELECT id AS user_id, login, password\n" +
                "            FROM \"user\" OFFSET ? FETCH FIRST ? ROWS ONLY),\n" +
                "     chat AS (SELECT c.id AS chatroom_id, c.name AS room_name, owner_id,\n" +
                "            u.login AS owner_login, u.password AS owner_password\n" +
                "            FROM chatroom c\n" +
                "            JOIN \"user\" u on c.owner_id = u.id),\n" +
                "     created AS (SELECT user_id, login, password,\n" +
                "            chatroom_id, room_name, owner_id, owner_login, owner_password,\n" +
                "            CASE WHEN c.room_name IS NULL THEN 'none'\n" +
                "                ELSE 'created' END AS type FROM us\n" +
                "            LEFT JOIN chat c on c.owner_id = us.user_id),\n" +
                "     social AS (SELECT us.user_id, login, password,\n" +
                "            m.chatroom_id, room_name, owner_id, owner_login, owner_password,\n" +
                "            'social' AS type FROM us\n" +
                "            JOIN message m on m.author_id = us.user_id\n" +
                "            JOIN chat c on m.chatroom_id = c.chatroom_id)\n" +
                "SELECT * FROM created\n" +
                "UNION\n" +
                "SELECT * FROM social\n" +
                "ORDER BY user_id, type";
        int offset = page * size;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, offset);
            statement.setInt(2, size);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                Long userId = rs.getLong("user_id");
                User user;
                if (userList.stream().noneMatch(u -> userId.equals(u.getId()))) {
                    user = new User(userId, rs.getString("login"), rs.getString("password"));
                    userList.add(user);
                } else {
                    user = userList.stream().filter(u -> userId.equals(u.getId())).collect(Collectors.toList()).get(0);
                }

                String chatType = rs.getString("type");
                if (!chatType.equals("none")) {
                    Chatroom chat = new Chatroom(rs.getLong("chatroom_id"),
                            rs.getString("room_name"),
                            new User(rs.getLong("owner_id"),
                                    rs.getString("owner_login"),
                                    rs.getString("owner_password")));
                    if (chatType.equals("created")) {
                        user.getCreatedChats().add(chat);
                    } else {
                        user.getSocialChats().add(chat);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("An error in FINDING ALL in UserRepository!");
        }
        return userList;
    }
}
