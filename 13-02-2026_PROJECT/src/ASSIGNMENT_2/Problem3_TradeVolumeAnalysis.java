package exercise;

import java.util.Arrays;

public class Problem3_TradeVolumeAnalysis {

    static class Trade {
        String id;
        int volume;

        public Trade(String id, int volume) {
            this.id = id;
            this.volume = volume;
        }

        @Override
        public String toString() {
            return id + ":" + volume;
        }
    }

    public static void main(String[] args) {
        Trade[] morningTrades = {
                new Trade("T3", 500),
                new Trade("T1", 100),
                new Trade("T2", 300)
        };

        Trade[] afternoonTrades = {
                new Trade("T5", 400),
                new Trade("T4", 200)
        };

        System.out.println("--- Merge Sort (ASC Volume) ---");
        mergeSort(morningTrades, 0, morningTrades.length - 1);
        System.out.println(Arrays.toString(morningTrades));

        System.out.println("\n--- Quick Sort (DESC Volume) ---");
        quickSort(morningTrades, 0, morningTrades.length - 1);
        System.out.println(Arrays.toString(morningTrades));

        System.out.println("\n--- Merging Morning and Afternoon ---");
        // Ensure both are sorted ASC before merging
        mergeSort(morningTrades, 0, morningTrades.length - 1);
        mergeSort(afternoonTrades, 0, afternoonTrades.length - 1);
        Trade[] allTrades = mergeTwoLists(morningTrades, afternoonTrades);
        System.out.println(Arrays.toString(allTrades));

        int totalVolume = 0;
        for (Trade t : allTrades) totalVolume += t.volume;
        System.out.println("\nTotal Volume: " + totalVolume);
    }

    // --- Merge Sort Logic ---
    public static void mergeSort(Trade[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(Trade[] arr, int left, int mid, int right) {
        Trade[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        Trade[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i].volume <= rightArr[j].volume) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < leftArr.length) arr[k++] = leftArr[i++];
        while (j < rightArr.length) arr[k++] = rightArr[j++];
    }

    // --- Quick Sort Logic (DESC) ---
    public static void quickSort(Trade[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(Trade[] arr, int low, int high) {
        int pivot = arr[high].volume;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            // Change to > for DESC order
            if (arr[j].volume > pivot) {
                i++;
                Trade temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Trade temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // --- Merge Two Sorted Lists ---
    public static Trade[] mergeTwoLists(Trade[] list1, Trade[] list2) {
        Trade[] result = new Trade[list1.length + list2.length];
        int i = 0, j = 0, k = 0;
        while (i < list1.length && j < list2.length) {
            if (list1[i].volume <= list2[j].volume) result[k++] = list1[i++];
            else result[k++] = list2[j++];
        }
        while (i < list1.length) result[k++] = list1[i++];
        while (j < list2.length) result[k++] = list2[j++];
        return result;
    }
}