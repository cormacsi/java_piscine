package ex04;

public class Program {
    public static void main(String[] args) {
        TransactionsService bank = new TransactionsService();

        Integer id = bank.addUser(new User("Mike", 145));
        Integer id2 = bank.addUser(new User("John", 56));

        System.out.println("User id: " + id + " Balance: " + bank.getBalance(id));
        System.out.println("User id: " + id2 + " Balance: " + bank.getBalance(id2));
        System.out.println();
        bank.performTransaction(id, id2, 100);
        bank.performTransaction(id, id2, 15);
        System.out.println();

        for (Transaction transfer : bank.getUserTransactions(id)) {
            System.out.println(transfer);
        }

        for (Transaction transfer : bank.getUserTransactions(id2)) {
            System.out.println(transfer);
        }
        System.out.println();
        System.out.println("User id: " + id + " Balance: " + bank.getBalance(id));
        System.out.println("User id: " + id2 + " Balance: " + bank.getBalance(id2));
        System.out.println();

        System.out.println("\nRemoving 2 transactions!");
        Transaction[] list = bank.getUserTransactions(id);
        bank.removeTransaction(id, list[0].getId());
        bank.removeTransaction(id, list[1].getId());

        for (Transaction transfer : bank.getUserTransactions(id)) {
            System.out.println(transfer);
        }

        for (Transaction transfer : bank.getUserTransactions(id2)) {
            System.out.println(transfer);
        }
        System.out.println("\nUnpaired transactions:");
        Transaction[] unpaired = bank.getInvalidTransactions();
        for (Transaction pair : unpaired) {
            System.out.println(pair);
        }

        System.out.println("\nRemoving other 2 transactions!");
        Transaction[] list2 = bank.getUserTransactions(id2);
        bank.removeTransaction(id2, list2[0].getId());
        bank.removeTransaction(id2, list2[1].getId());

        System.out.println("\nUnpaired transactions:");
        unpaired = bank.getInvalidTransactions();
        for (Transaction pair : unpaired) {
            System.out.println(pair);
        }
    }
}