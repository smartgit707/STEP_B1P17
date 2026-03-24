package exercise;

import java.util.Arrays;

public class Problem2_ClientRiskRanking {

    static class Client {
        String name;
        int riskScore;
        double accountBalance;

        public Client(String name, int riskScore, double accountBalance) {
            this.name = name;
            this.riskScore = riskScore;
            this.accountBalance = accountBalance;
        }

        @Override
        public String toString() {
            return name + ":" + riskScore + " ($" + accountBalance + ")";
        }
    }

    public static void main(String[] args) {
        Client[] clients = {
                new Client("ClientC", 80, 5000.0),
                new Client("ClientA", 20, 10000.0),
                new Client("ClientB", 50, 7500.0),
                new Client("ClientD", 80, 2000.0)
        };

        System.out.println("--- Bubble Sort (ASC Risk Score) ---");
        bubbleSortAsc(Arrays.copyOf(clients, clients.length));

        System.out.println("\n--- Insertion Sort (DESC Risk + DESC Balance) ---");
        Client[] sortedDesc = insertionSortDesc(Arrays.copyOf(clients, clients.length));

        System.out.println("\n--- Top 3 Highest Risk Clients ---");
        for (int i = 0; i < Math.min(3, sortedDesc.length); i++) {
            System.out.println((i + 1) + ". " + sortedDesc[i]);
        }
    }

    public static void bubbleSortAsc(Client[] arr) {
        int n = arr.length;
        int swaps = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    System.out.println("Swapping " + arr[j].name + " with " + arr[j+1].name);
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                }
            }
        }
        System.out.println("Result: " + Arrays.toString(arr));
        System.out.println("Total Swaps: " + swaps);
    }

    public static Client[] insertionSortDesc(Client[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Client key = arr[i];
            int j = i - 1;

            while (j >= 0 && shouldSwapDesc(arr[j], key)) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        System.out.println("Result: " + Arrays.toString(arr));
        return arr;
    }

    private static boolean shouldSwapDesc(Client current, Client key) {
        if (current.riskScore < key.riskScore) return true;
        if (current.riskScore == key.riskScore) {
            return current.accountBalance < key.accountBalance;
        }
        return false;
    }
}
