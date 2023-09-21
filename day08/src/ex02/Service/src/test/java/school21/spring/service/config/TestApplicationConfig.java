package school21.spring.service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "school21.spring.service")
public class TestApplicationConfig {

    @Bean(name = {"hikariDataSource", "driverManagerDataSource"})
    public DataSource dataSource() {
       return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScripts("schema.sql", "data.sql")
                .build();
    }

    @Bean("usersService")
    public UsersService getUsersServiceTemplate(@Qualifier("jdbc") UsersRepository usersRepository) {
        return new UsersServiceImpl(usersRepository);
    }
}
