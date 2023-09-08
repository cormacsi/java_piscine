package ex05;

import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private static final String menu = """
            1. Add a user
            2. View user balances
            3. Perform a transfer
            4. View all transactions for a specific user
            5. DEV – remove a transfer by ID
            6. DEV – check transfer validity
            7. Finish execution""";

    private static final String ERROR = "Error! Invalid argument";
    private static final String USERROR = "Error! User not found!";
    private static final String ONE = "Enter a user name and a balance";
    private static final String TWO = "Enter a user ID";
    private static final String THREE = "Enter a sender ID, a recipient ID, and a transfer amount";
    private static final String FOUR = "Enter a user ID";
    private static final String FIVE = "Enter a user ID and a transfer ID";

    private static final String SIX = "%s(id = %d) has an unacknowledged transfer id = %s from %s(id = %d) for %d\n";
    private static final String ENDLINE = "---------------------------------------------------------";
    private final Scanner scan;
    private final TransactionsService bank;

    public Menu() {
        this.scan = new Scanner(System.in);
        this.bank = new TransactionsService();
        bank.addUser(new User("Jonh", 777));
        bank.addUser(new User("Mike", 100));
        bank.performTransaction(1, 2, 100);
        bank.performTransaction(1, 2, 150);
    }
    public void start() {
        int button = 0;
        String line = new String();
        while (button != 7) {
            System.out.println(menu);
            line = scan.nextLine();
            if (line.matches("[1-7]")) {
                button = Integer.parseInt(line);
                if (button < 7) program(button);
            } else {
                System.err.println(ERROR);
            }
            System.out.println(ENDLINE);
        }
        scan.close();
    }
    private void program(int num) {
        if (num == 1) actionOne();
        else if (num == 2) actionTwo();
        else if (num == 3) actionThree();
        else if (num == 4) actionFour();
        else if (num == 5) actionFive();
        else actionSix();
    }

    private void actionOne() {
        System.out.println(ONE);
        String[] arg = scan.nextLine().split(" ");
        if (arg.length == 2 && arg[0].matches("[a-zA-Z]+") && arg[1].matches("[+-]?\\d+")) {
            User user = new User(arg[0], Integer.parseInt(arg[1]));
            bank.addUser(user);

            System.out.printf("User with id = %d is added\n", user.getId());
            System.out.printf("Name: %s Balance: %d\n", user.getName(), user.getBalance());
        } else {
            System.err.println(ERROR);
        }
    }

    private void actionTwo() {
        System.out.println(TWO);
        String input = scan.nextLine();
        if (input.matches("\\d+")) {
            Integer userId = Integer.parseInt(input);
            try {
                System.out.println(bank.getUser(userId).getName() + " - " + bank.getBalance(userId));
            } catch (UserNotFoundException exp) {
                System.err.println(USERROR);
            }
        } else {
            System.err.println(ERROR);
        }
    }

    private void actionThree() {
        System.out.println(THREE);
        String[] input = scan.nextLine().split(" ");
        if (input.length == 3 && input[0].matches("\\d+") &&
                input[1].matches("\\d+") && input[2].matches("\\d+")) {

            Integer senderId = Integer.parseInt(input[0]);
            Integer recipientId = Integer.parseInt(input[1]);
            Integer amount = Integer.parseInt(input[2]);

            try {
                bank.performTransaction(senderId, recipientId, amount);
            } catch (UserNotFoundException exp) {
                System.err.println(USERROR);
            } catch (IllegalTransactionException e) {
                System.err.println("The transfer amount is not valid");
            }
        } else {
            System.err.println(ERROR);
        }
    }

    private void actionFour() {
        System.out.println(FOUR);
        String input = scan.nextLine();
        if (input.matches("\\d+")) {
            Transaction[] transfer;
            Integer userId = Integer.parseInt(input);
            try {
                transfer = bank.getUserTransactions(userId);
                for (Transaction t : transfer) {
                    System.out.println(getTransferData(t, userId));
                }
            } catch (UserNotFoundException exp) {
                System.err.println(USERROR);
            }
        } else {
            System.err.println(ERROR);
        }
    }

    private String getTransferData(Transaction t, Integer userId) {
        User recipient = t.getRecipient();
        String result = (t.getTransferCategory() == Category.CREDIT ? "To " : "From ");
        result += String.format("%s(id = %d) %d with id = %s\n", recipient.getName(), recipient.getId(), t.getTransferAmount(), t.getId());
        return result;
    }

    private void actionFive() {
        System.out.println(FIVE);
        String[] input = scan.nextLine().split(" ");
        if (input.length == 2 && input[0].matches("\\d+")) {
            Integer userId = Integer.parseInt(input[0]);
            try {
                UUID transferId = UUID.fromString(input[1]);
                Transaction transaction = bank.removeTransaction(userId, transferId);
                User recipient = transaction.getRecipient();
                String result = "Transfer ";
                result += (transaction.getTransferCategory() == Category.CREDIT ? "To " : "From ");
                result += String.format("%s(id = %d) %d removed\n", recipient.getName(), recipient.getId(), Math.abs(transaction.getTransferAmount()));
                System.out.println(result);
            } catch (UserNotFoundException exp) {
                System.err.println(USERROR);
            } catch (TransactionNotFoundException exp) {
                System.err.println("Error! Transaction not found");
            } catch (IllegalArgumentException exp) {
                System.err.println("Transfer ID is not valid");
            }
        } else {
            System.err.println(ERROR);
        }
    }

    private void actionSix() {
        System.out.println("Check results:");
        Transaction[] invalid = bank.getInvalidTransactions();

        for (Transaction t : invalid) {
            User sender = t.getSender();
            User recipient = t.getRecipient();
            System.out.printf(SIX, sender.getName(), sender.getId(), t.getId().toString(), recipient.getName(), recipient.getId(), t.getTransferAmount());
        }
    }
}
