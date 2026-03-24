package exercise;

import java.util.Arrays;

public class Problem4_PortfolioReturnSort {

    static class Asset {
        String ticker;
        double returnRate;
        double volatility;

        public Asset(String ticker, double returnRate, double volatility) {
            this.ticker = ticker;
            this.returnRate = returnRate;
            this.volatility = volatility;
        }

        @Override
        public String toString() {
            return ticker + ":" + returnRate + "% (Vol:" + volatility + ")";
        }
    }

    public static void main(String[] args) {
        Asset[] assets = {
                new Asset("AAPL", 12.0, 15.5),
                new Asset("TSLA", 8.0, 25.0),
                new Asset("GOOG", 15.0, 12.0),
                new Asset("MSFT", 12.0, 10.0) // Same return as AAPL to test stability
        };

        System.out.println("--- Merge Sort (ASC Return Rate - Stable) ---");
        Asset[] mergeData = Arrays.copyOf(assets, assets.length);
        mergeSort(mergeData, 0, mergeData.length - 1);
        System.out.println(Arrays.toString(mergeData));

        System.out.println("\n--- Quick Sort (DESC Return + ASC Volatility) ---");
        Asset[] quickData = Arrays.copyOf(assets, assets.length);
        quickSort(quickData, 0, quickData.length - 1);
        System.out.println(Arrays.toString(quickData));
    }

    // --- Merge Sort Logic ---
    public static void mergeSort(Asset[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(Asset[] arr, int left, int mid, int right) {
        Asset[] leftArr = Arrays.copyOfRange(arr, left, mid + 1);
        Asset[] rightArr = Arrays.copyOfRange(arr, mid + 1, right + 1);

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i].returnRate <= rightArr[j].returnRate) {
                arr[k++] = leftArr[i++];
            } else {
                arr[k++] = rightArr[j++];
            }
        }
        while (i < leftArr.length) arr[k++] = leftArr[i++];
        while (j < rightArr.length) arr[k++] = rightArr[j++];
    }

    // --- Quick Sort Logic with Median-of-Three Pivot ---
    public static void quickSort(Asset[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(Asset[] arr, int low, int high) {
        // Median-of-Three Pivot Selection
        int mid = low + (high - low) / 2;
        int pivotIndex = selectMedian(arr, low, mid, high);

        // Swap pivot with high
        Asset tempPivot = arr[pivotIndex];
        arr[pivotIndex] = arr[high];
        arr[high] = tempPivot;

        Asset pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            // DESC returnRate, if tie then ASC volatility
            if (compareQuick(arr[j], pivot)) {
                i++;
                Asset temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Asset temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    private static int selectMedian(Asset[] arr, int a, int b, int c) {
        if ((arr[a].returnRate - arr[b].returnRate) * (arr[c].returnRate - arr[a].returnRate) >= 0) return a;
        else if ((arr[b].returnRate - arr[a].returnRate) * (arr[c].returnRate - arr[b].returnRate) >= 0) return b;
        else return c;
    }

    private static boolean compareQuick(Asset current, Asset pivot) {
        if (current.returnRate > pivot.returnRate) return true;
        if (current.returnRate == pivot.returnRate) {
            return current.volatility < pivot.volatility;
        }
        return false;
    }
}
