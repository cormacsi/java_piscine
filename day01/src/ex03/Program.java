package ex03;

public class Program {
    public static void main(String[] args) {
        UsersArrayList users = new UsersArrayList();
        System.out.println("Created users list! Size: " + users.getSize());
        users.addUser(new User("Mike", 60));
        users.addUser(new User("Peter", 100));

        System.out.println("Added users! Size: " + users.getSize());
        System.out.println(users + "\n");

        TransactionsLinkedList transactions = new TransactionsLinkedList();
        System.out.println("Created transactions list! Size: " + transactions.getLength());
        Transaction[] list = transactions.toArray();
        for (Transaction t : list) {
            System.out.println(t);
        }
        System.out.println();

        Transaction transaction1 = new Transaction(users.getByIndex(0), users.getByIndex(1), 40);
        transactions.addTransaction(transaction1);
        System.out.println("Added 1 transactions! Size: " + transactions.getLength());
        list = transactions.toArray();
        for (Transaction t : list) {
            System.out.println(t);
        }
        System.out.println();

        Transaction transaction2 = new Transaction(users.getByIndex(1), users.getByIndex(0), -40);
        transactions.addTransaction(transaction2);
        System.out.println("Added 2 transactions! Size: " + transactions.getLength());
        list = transactions.toArray();
        for (Transaction t : list) {
            System.out.println(t);
        }
        System.out.println();

        transactions.removeTransaction(transaction1.getId());
        System.out.println("Removing transaction 1! Size: " + transactions.getLength());
        list = transactions.toArray();
        for (Transaction t : list) {
            System.out.println(t);
        }
        System.out.println();

        transactions.removeTransaction(transaction2.getId());
        System.out.println("Removing transaction 2! Size: " + transactions.getLength());
        list = transactions.toArray();
        for (Transaction t : list) {
            System.out.println(t);
        }
        System.out.println();
    }
}