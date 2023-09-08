package ex01;

public class UserIdsGenerator {
    private static Integer counter = 1;

    private static final UserIdsGenerator SINGLETON = new UserIdsGenerator();

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        return SINGLETON;
    }
    public static Integer generateId() {
        return counter++;
    }
}
