package ex00;

public class Program {
    public static void main(String[] args) {
        User mike = new User("Mike", 60);
        User peter = new User("Peter", 100);
        User nullUser = new User(null, 60);
        User kate = new User("Kate", -100);
        System.out.println(mike);
        System.out.println(peter);
        System.out.println(nullUser);
        System.out.println(kate);

        Transaction transaction1 = new Transaction(mike, peter, 30);
        Transaction transaction2 = new Transaction(peter, mike, -30);

        System.out.println(transaction1);
        System.out.println(transaction2);

        Transaction transaction3 = new Transaction(mike, peter, -200);
        Transaction transaction4 = new Transaction(peter, mike, 200);

        System.out.println(transaction3);
        System.out.println(transaction4);
    }
}