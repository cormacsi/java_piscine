package school21.spring.service.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<User> userRowMapper = (resultSet, rowNum) -> new User(resultSet.getLong("id"),
            resultSet.getString("email"));

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate
                .queryForObject("SELECT * FROM users WHERE id=?", userRowMapper, id));
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper);
    }

    @Override
    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO users (email) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getEmail());
            return ps;
        }, keyHolder);
        Map<String, Object> returnedKeys = keyHolder.getKeys();
        if (returnedKeys != null) {
            entity.setId((Long) keyHolder.getKeys().get("id"));
        } else {
            System.out.println("Save method has not returned generated keys!");
        }
    }

    @Override
    public void update(User entity) {
        if (jdbcTemplate.update("UPDATE users SET email=? WHERE id=?", entity.getEmail(), entity.getId()) == 0) {
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
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", userRowMapper, email));
    }
}
