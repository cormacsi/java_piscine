package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?")) {
            statement.setLong(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                user = new User(id, resultSet.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("An error in findById method of UsersRepositoryJdbcImpl");
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> usersList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users")) {
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                usersList.add(new User(resultSet.getLong("id"),
                        resultSet.getString("email")));
            }
        } catch (SQLException e) {
            System.err.println("An error in findAll method of UsersRepositoryJdbcImpl");
        }
        return usersList;
    }

    @Override
    public void save(User entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (email) VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getEmail());
            statement.execute();
            ResultSet key = statement.getGeneratedKeys();
            if (key.next()) {
                entity.setId(key.getLong("id"));
            } else {
                System.err.println("The id key was not returned!");
            }
        } catch (SQLException e) {
            System.err.println("An error in save method of UsersRepositoryJdbcImpl");
        }
    }

    @Override
    public void update(User entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET email=? WHERE id=?")) {
            statement.setString(1, entity.getEmail());
            statement.setLong(2, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            System.err.println("An error in update method of UsersRepositoryJdbcImpl");
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.err.println("An error in delete method of UsersRepositoryJdbcImpl");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email=?")) {
            statement.setString(1, email);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                user = new User(resultSet.getLong("id"), email);
            }
        } catch (SQLException e) {
            System.err.println("An error in findByEmail method of UsersRepositoryJdbcImpl");
        }
        return Optional.ofNullable(user);
    }
}
