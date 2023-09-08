package ex04;

import java.util.UUID;

public class TransactionsService {
    private UsersList userList;

    private TransactionsList unpaired;

    public TransactionsService() {
        this.userList = new UsersArrayList();
        this.unpaired = new TransactionsLinkedList();
    }

    public Integer addUser(User user) {
        userList.addUser(user);
        return user.getId();
    }

    public Integer getBalance(Integer id) {
        return userList.getById(id).getBalance();
    }

    public void performTransaction(Integer senId, Integer recId, Integer amount) {
        if (amount < 0 || getBalance(senId) < amount)
            throw new IllegalTransactionException("Amount exceeds the user's balance");

        User sender = userList.getById(senId);
        User recipient = userList.getById(recId);

        Transaction credit = new Transaction(sender, recipient, -amount);
        Transaction debit = new Transaction(credit.getId(), recipient, sender, amount);

        recipient.getTransactions().addTransaction(debit);
        sender.getTransactions().addTransaction(credit);

        recipient.setBalance(recipient.getBalance() + amount);
        sender.setBalance(sender.getBalance() - amount);

        System.out.println("The transfer is completed");
    }

    public Transaction[] getUserTransactions(Integer id) {
        return userList.getById(id).getTransactions().toArray();
    }

    public Transaction removeTransaction(Integer userId, UUID transId) throws UserNotFoundException, TransactionNotFoundException {
        User user = userList.getById(userId);
        Transaction transaction = user.getTransactions().removeTransaction(transId);
        try {
            unpaired.removeTransaction(transId);
        } catch (TransactionNotFoundException e) {
            User other = findPairedUser(user, transaction);
            Transaction otherTransaction = other.getTransactions().getById(transId);
            unpaired.addTransaction(otherTransaction);
        }
        return transaction;
    }

    private User findPairedUser(User user, Transaction transaction) {
        User sender = transaction.getSender();
        User recipient = transaction.getRecipient();
        return (user.equals(sender) ? recipient : sender);
    }

    public Transaction[] getInvalidTransactions() {
        return unpaired.toArray();
    }
}
