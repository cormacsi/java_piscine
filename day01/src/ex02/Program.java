package ex02;

public class Program {
    public static void main(String[] args) {
        UsersArrayList users = new UsersArrayList();
        System.out.println("Created array list! Size: " + users.getSize());
        users.addUser(new User("Mike", 60));
        users.addUser(new User("Peter", 100));
        users.addUser(new User(null, 60));
        users.addUser(new User("Kate", -100));

        users.addUser(new User("Mona", 45));
        users.addUser(new User("Jona", 56));

        users.addUser(new User("Ilona", 45));
        users.addUser(new User("Kira", 56));

        users.addUser(new User("Nika", 45));
        users.addUser(new User("Mara", 56));

        users.addUser(new User("Masha", 45));
        users.addUser(new User("Alex", 56));

        users.addUser(new User("Nobody", 45));
        users.addUser(new User("Ghost", 56));

        System.out.println("Added users! Size: " + users.getSize());
        System.out.println(users);

        System.out.println(users.getById(1));
        System.out.println(users.getById(10));
//        System.out.println(users.getById(20));
//        System.out.println(users.getById(-20));

        System.out.println(users.getByIndex(1));
        System.out.println(users.getByIndex(4));
//        System.out.println(users.getByIndex(-20));
//        System.out.println(users.getByIndex(20));
    }
}