package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Long signUp(String username, String password) {
        if (usersRepository.findByUsername(username).isPresent()) {
            return null;
        } else {
            return usersRepository.save(new User(null, username, passwordEncoder.encode(password)));
        }
    }

    @Override
    public boolean signIn(String username, String password) {
        Optional<User> user = usersRepository.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }


}
