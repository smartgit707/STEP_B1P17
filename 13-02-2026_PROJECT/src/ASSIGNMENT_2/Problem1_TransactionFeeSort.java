package exercise;

import java.util.ArrayList;
import java.util.List;

public class Problem1_TransactionFeeSort {

    static class Transaction {
        String id;
        double fee;
        String timestamp;

        public Transaction(String id, double fee, String timestamp) {
            this.id = id;
            this.fee = fee;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return id + ":" + fee + "@" + timestamp;
        }
    }

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));

        System.out.println("--- Original Transactions ---");
        System.out.println(transactions);

        System.out.println("\n--- Bubble Sort (Small Batch) ---");
        bubbleSortByFee(new ArrayList<>(transactions));

        System.out.println("\n--- Insertion Sort (Medium Batch) ---");
        insertionSortByFeeAndTimestamp(new ArrayList<>(transactions));

        System.out.println("\n--- High-Fee Outliers ---");
        identifyOutliers(transactions);
    }

    public static void bubbleSortByFee(List<Transaction> list) {
        int n = list.size();
        int swaps = 0;
        int passes = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            passes++;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                    swaps++;
                }
            }
            if (!swapped) break;
        }
        System.out.println("Result: " + list);
        System.out.println("Stats: " + passes + " passes, " + swaps + " swaps");
    }

    public static void insertionSortByFeeAndTimestamp(List<Transaction> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            while (j >= 0 && compareTransactions(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
        System.out.println("Result: " + list);
    }

    private static int compareTransactions(Transaction t1, Transaction t2) {
        if (t1.fee != t2.fee) {
            return Double.compare(t1.fee, t2.fee);
        }
        return t1.timestamp.compareTo(t2.timestamp);
    }

    public static void identifyOutliers(List<Transaction> list) {
        boolean found = false;
        for (Transaction t : list) {
            if (t.fee > 50.0) {
                System.out.println("FLAGGED: " + t);
                found = true;
            }
        }
        if (!found) System.out.println("None");
    }
}