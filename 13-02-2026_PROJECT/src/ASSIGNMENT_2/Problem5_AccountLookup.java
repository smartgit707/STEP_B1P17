package exercise;

import java.util.Arrays;

public class Problem5_AccountLookup {

    public static void main(String[] args) {
        String[] logs = {"accB", "accA", "accB", "accC"};
        String target = "accB";

        System.out.println("--- Linear Search (Unsorted) ---");
        linearSearch(logs, target);

        System.out.println("\n--- Binary Search (Sorted) ---");
        // Binary search requires a sorted array
        Arrays.sort(logs);
        System.out.println("Sorted Logs: " + Arrays.toString(logs));
        binarySearch(logs, target);
    }

    public static void linearSearch(String[] arr, String target) {
        int firstIndex = -1;
        int lastIndex = -1;
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                if (firstIndex == -1) firstIndex = i;
                lastIndex = i;
            }
        }

        System.out.println("Target: " + target);
        System.out.println("First Occurrence Index: " + firstIndex);
        System.out.println("Last Occurrence Index: " + lastIndex);
        System.out.println("Total Comparisons: " + comparisons + " (O(n))");
    }

    public static void binarySearch(String[] arr, String target) {
        int low = 0;
        int high = arr.length - 1;
        int foundIndex = -1;
        int comparisons = 0;

        while (low <= high) {
            comparisons++;
            int mid = low + (high - low) / 2;
            int res = target.compareTo(arr[mid]);

            if (res == 0) {
                foundIndex = mid;
                break;
            } else if (res > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (foundIndex != -1) {
            int count = countOccurrences(arr, foundIndex, target);
            System.out.println("Target: " + target);
            System.out.println("Match found at Index: " + foundIndex);
            System.out.println("Total Occurrences: " + count);
            System.out.println("Comparisons to find mid: " + comparisons + " (O(log n))");
        } else {
            System.out.println("Target not found.");
        }
    }

    private static int countOccurrences(String[] arr, int index, String target) {
        int count = 1;
        // Check left
        for (int i = index - 1; i >= 0 && arr[i].equals(target); i--) count++;
        // Check right
        for (int i = index + 1; i < arr.length && arr[i].equals(target); i++) count++;
        return count;
    }
}