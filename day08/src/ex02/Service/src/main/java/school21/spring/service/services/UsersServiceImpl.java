package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.UUID;

@Service("usersServiceTemplate")
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("jdbcTemplate") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        if (email == null || email.isEmpty()) {
            System.err.println("The email is not valid!");
            return null;
        }
        UUID password = UUID.randomUUID();
        usersRepository.save(new User(null, email, password.toString()));
        if (usersRepository.findByEmail(email).isPresent()) {
            return password.toString();
        }
        return null;
    }
}
