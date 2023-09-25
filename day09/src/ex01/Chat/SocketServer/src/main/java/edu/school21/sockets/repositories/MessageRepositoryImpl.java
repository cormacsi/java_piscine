package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
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
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    private final JdbcTemplate jdbcTemplate;

    private UsersRepository usersRepository;

    @Autowired
    public MessageRepositoryImpl(DataSource dataSource, UsersRepository usersRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.usersRepository = usersRepository;
    }

    private final RowMapper<Message> messageRowMapper = (resultSet, rowNum) -> {
        Optional<User> sender = usersRepository.findById(resultSet.getLong("sender_id"));
        if (sender.isPresent()) {
            return new Message(resultSet.getLong("id"),
                    resultSet.getString("text"),
                    resultSet.getTimestamp("time").toLocalDateTime(),
                    sender.get());
        } else {
            return null;
        }
    };

    @Override
    public Optional<Message> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate
                    .queryForObject("SELECT * FROM messages WHERE id=?", messageRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query("SELECT * FROM messages", messageRowMapper);
    }

    @Override
    public Long save(Message entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO messages (text, time, sender_id) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getText());
            ps.setTimestamp(2, Timestamp.valueOf(entity.getTime()));
            ps.setLong(3, entity.getSender().getId());
            return ps;
            }, keyHolder);
        Map<String, Object> returnedKeys = keyHolder.getKeys();
        if (returnedKeys != null) {
            entity.setId((Long) keyHolder.getKeys().get("id"));
            return entity.getId();
        } else {
            System.err.println("Save message method has not returned generated keys!");
            return null;
        }
    }

    @Override
    public void update(Message entity) {
        if (jdbcTemplate.update("UPDATE messages SET text=?, time=?, sender_id=? WHERE id=?",
                entity.getText(), Timestamp.valueOf(entity.getTime()),
                entity.getSender().getId(), entity.getId()) == 0) {
            System.err.println("An error in updating a message!");
        }
    }

    @Override
    public void delete(Long id) {
        if (jdbcTemplate.update("DELETE FROM messages WHERE id=?", id) == 0) {
            System.err.println("An error in deleting a message!");
        }
    }
}
