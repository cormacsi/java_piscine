package school21.spring.service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;

import javax.sql.DataSource;
import java.sql.SQLException;

public class UsersServiceImplTest {

    static ApplicationContext context;

    static UsersService usersService;

    static UsersService usersServiceTemplate;

    @BeforeAll
    static void beforeAll() {
        context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        usersService = context.getBean("usersService", UsersService.class);
        usersServiceTemplate = context.getBean("usersServiceTemplate", UsersService.class);
    }

    @Test
    void testDataSource() throws SQLException {
        DataSource dataSource = context.getBean("hikariDataSource", DataSource.class);
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(dataSource.getConnection());
    }

    @Test
    void testDataSourceDriver() throws SQLException {
        DataSource dataSource = context.getBean("driverManagerDataSource", DataSource.class);
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(dataSource.getConnection());
    }

    @Test
    void testUserService() {
        Assertions.assertNotNull(usersService);
    }

    @Test
    void testUserServiceTemplate() {
        Assertions.assertNotNull(usersServiceTemplate);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1_JDBC@email.com", "2_JDBC@email.com", "3_JDBC@email.com"})
    void testSignUp(String email) {
        Assertions.assertNotNull(usersService.signUp(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1_TEMPLATE@email.com", "2_TEMPLATE@email.com", "3_TEMPLATE@email.com"})
    void testSignUpTemplate(String email) {
        Assertions.assertNotNull(usersServiceTemplate.signUp(email));
    }
}
