package exercise;

import java.util.Arrays;

public class Problem6_RiskThresholdLookup {

    public static void main(String[] args) {
        int[] riskBands = {10, 25, 50, 100};
        int threshold = 30;

        System.out.println("Sorted Risk Bands: " + Arrays.toString(riskBands));
        System.out.println("Target Threshold: " + threshold);

        System.out.println("\n--- Linear Search ---");
        linearSearch(riskBands, threshold);

        System.out.println("\n--- Binary Search (Floor & Ceiling) ---");
        findFloorCeiling(riskBands, threshold);
    }

    public static void linearSearch(int[] arr, int target) {
        boolean found = false;
        int comps = 0;
        for (int val : arr) {
            comps++;
            if (val == target) {
                found = true;
                break;
            }
        }
        System.out.println("Threshold " + target + (found ? " found" : " not found"));
        System.out.println("Comparisons: " + comps);
    }

    public static void findFloorCeiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int floor = -1;
        int ceiling = -1;
        int comps = 0;

        while (low <= high) {
            comps++;
            int mid = low + (high - low) / 2;

            if (arr[mid] == target) {
                floor = arr[mid];
                ceiling = arr[mid];
                break;
            } else if (arr[mid] < target) {
                floor = arr[mid]; // Current mid is a potential floor
                low = mid + 1;
            } else {
                ceiling = arr[mid]; // Current mid is a potential ceiling
                high = mid - 1;
            }
        }

        System.out.println("Floor (" + target + "): " + (floor != -1 ? floor : "None"));
        System.out.println("Ceiling (" + target + "): " + (ceiling != -1 ? ceiling : "None"));
        System.out.println("Comparisons: " + comps);
    }
}