package edu.school21.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.classes.OrmManager;
import edu.school21.models.Car;
import edu.school21.models.User;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class Program {

    private static DataSource data;

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    private static final String USERNAME = "user";

    private static final String PASSWORD = "admin";

    private static final String delimiter = "------------------------------------------------------------------";

    public static void main(String[] args) {
        createConnection();
        OrmManager ormManager = new OrmManager(data);
        userTest(ormManager);
        carTest(ormManager);
    }

    private static void createConnection() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        data = new HikariDataSource(config);
    }

    private static void userTest(OrmManager ormManager) {
        System.out.println(delimiter);
        System.out.println("USER TESTS:");
        User user = new User(null, "Peter", "Collins", 25);
        ormManager.save(user);
        System.out.println("User saved: " + user);
        System.out.println(delimiter);
        user.setFirstName("Nick");
        user.setAge(22);
        ormManager.update(user);
        System.out.println("User updates: " + user);
        System.out.println(delimiter);
        Object found = ormManager.findById(1L, user.getClass());
        if (found != null) {
            System.out.println("User with id 1 was found: " + found);
        } else {
            System.err.println("User not found!");
        }
        System.out.println(delimiter);
    }

    private static void carTest(OrmManager ormManager) {
        System.out.println("CAR TESTS:");
        List<Car> carList = new ArrayList<>();
        carList.add(new Car(null, "Ford", 121.2, true));
        carList.add(new Car(null, "Mazda", 210., false));
        carList.add(new Car(null, "Porsche", 320., false));
        for (Car car : carList) {
            ormManager.save(car);
            System.out.println("Car saved: " + car);
        }
        System.out.println(delimiter);
        Car car = carList.get(0);
        car.setModel("Ford Focus");
        car.setHorsePower(140.1);
        car.setSecondHand(false);
        ormManager.update(car);
        System.out.println("Car updates: " + car);
        System.out.println(delimiter);
        Object found = ormManager.findById(3L, car.getClass());
        if (found != null) {
            System.out.println("Car with id 3 was found: " + found);
        } else {
            System.err.println("Car not found!");
        }
        System.out.println(delimiter);
    }
}
