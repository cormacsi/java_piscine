package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<User> userRowMapper = (resultSet, rowNum) -> new User(resultSet.getLong("id"),
            resultSet.getString("username"), resultSet.getString("password"));

    @Override
    public Optional<User> findById(Long id) {
        try{
            return Optional.ofNullable(jdbcTemplate
                    .queryForObject("SELECT * FROM users WHERE id=?", userRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper);
    }

    @Override
    public Long save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            return ps;
        }, keyHolder);
        Map<String, Object> returnedKeys = keyHolder.getKeys();
        if (returnedKeys != null) {
            entity.setId((Long) keyHolder.getKeys().get("id"));
            return entity.getId();
        } else {
            System.err.println("Save user method has not returned generated keys!");
            return null;
        }
    }

    @Override
    public void update(User entity) {
        if (jdbcTemplate.update("UPDATE users SET username=?, password=? WHERE id=?",
                entity.getUsername(), entity.getPassword(), entity.getId()) == 0) {
            System.err.println("An error in updating a user!");
        }
    }

    @Override
    public void delete(Long id) {
        if (jdbcTemplate.update("DELETE FROM users WHERE id=?", id) == 0) {
            System.err.println("An error in deleting a user!");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable(jdbcTemplate
                    .queryForObject("SELECT * FROM users WHERE username=?", userRowMapper, username));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}