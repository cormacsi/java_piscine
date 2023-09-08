package ex03;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Node first;

    private Node last;

    private Integer length;

    public TransactionsLinkedList() {
        first = null;
        last = null;
        length = 0;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        if (first == null) {
            first = new Node(null, null, transaction);
        } else if (last == null) {
            last = new Node(first, null, transaction);
            first.next = last;
        } else {
            last.next = new Node(last, null, transaction);
            last = last.next;
        }
        length++;
    }

    @Override
    public void removeTransaction(UUID id) {
        Node node = first;
        while (node != null) {
            if (node.transaction.getId().equals(id)) {
                Node prev = node.prev;
                Node next = node.next;
                if (prev == null) first = next;
                else prev.next = next;
                if (next == null) last = prev;
                else next.prev = prev;
                length--;
                return;
            }
            node = node.next;
        }
        throw new TransactionNotFoundException("Transaction not found");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] array = new Transaction[length];
        Node node = first;
        for (int i = 0; i < length && node != null; i++) {
            array[i] = node.transaction;
            node = node.next;
        }
        return array;
    }

    private static class Node {
        private Node prev;
        private Node next;
        private Transaction transaction;

        public Node(Node prev, Node next, Transaction transaction) {
            this.prev = prev;
            this.next = next;
            this.transaction = transaction;
        }
    }
}
