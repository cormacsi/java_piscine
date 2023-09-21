package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        testUsersRepositoryJdbc(usersRepository);

        usersRepository = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
        testUsersRepositoryJdbcTemplate(usersRepository);
    }

    private static void testUsersRepositoryJdbc(UsersRepository usersRepository) {
        System.out.println("-------- UsersRepositoryJdbc --------");
        System.out.println("FIND ALL:");
        System.out.println(usersRepository.findAll());

        User user = new User(null, "katrine@gmail.com");
        usersRepository.save(user);
        System.out.println("User SAVED: " + user);

        user.setEmail("katrineNEWEMAIL@gmail.com");
        usersRepository.update(user);
        System.out.println("User UPDATED: " + user);

        System.out.println("User FOUND BY ID: " + usersRepository.findById(user.getId()));

        System.out.println("User FOUND BY EMAIL: " + usersRepository.findByEmail(user.getEmail()));

        usersRepository.delete(user.getId());
        System.out.println("DELETED user with id: " + user.getId());
    }

    private static void testUsersRepositoryJdbcTemplate(UsersRepository usersRepository) {
        System.out.println("-------- UsersRepositoryJdbcTemplate --------");
        System.out.println("FIND ALL:");
        System.out.println(usersRepository.findAll());

        User user = new User(null, "romano@gmail.com");
        usersRepository.save(user);
        System.out.println("User SAVED: " + user);

        user.setEmail("romanoNEWEMAIL@gmail.com");
        usersRepository.update(user);
        System.out.println("User UPDATED: " + user);

        System.out.println("User FOUND BY ID: " + usersRepository.findById(user.getId()));

        System.out.println("User FOUND BY EMAIL: " + usersRepository.findByEmail(user.getEmail()));

        usersRepository.delete(user.getId());
        System.out.println("DELETED user with id: " + user.getId());
    }
}
