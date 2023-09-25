package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UsersRepository usersRepository;

    public void saveMessage(String text, String username) {
        Optional<User> sender = usersRepository.findByUsername(username);
        if (sender.isPresent()) {
            if (messageRepository.save(new Message(null, text, LocalDateTime.now(), sender.get())) == null) {
                System.err.println("The message was not saved!");
            }
        } else {
            System.err.println("The sender user does not exist!");
        }
    }
}
