package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.ApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersService usersService = ctx.getBean("usersServiceTemplate", UsersService.class);
        String email = "signUpMail@gmail.com";
        String password = usersService.signUp(email);
        System.out.println("Signed up user with email: " + email);
        System.out.println("Got password: " + password);

        UsersRepository usersRepository = ctx.getBean("jdbcTemplate", UsersRepository.class);
        Optional<User> user = usersRepository.findByEmail(email);
        user.ifPresent(value -> System.out.println("Found user: " + value));
        user.ifPresent(value -> usersRepository.delete(value.getId()));
    }
}
