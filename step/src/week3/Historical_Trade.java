package week3;

import java.util.*;

class Trade {
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

public class Historical_Trade {

    // ---------------------- MERGE SORT (ASCENDING) --------------------------
    public static void mergeSort(Trade[] arr) {
        if (arr.length < 2) return;
        mergeSortRecursive(arr, 0, arr.length - 1);
    }

    private static void mergeSortRecursive(Trade[] arr, int left, int right) {
        if (left >= right) return;

        int mid = (left + right) / 2;

        mergeSortRecursive(arr, left, mid);
        mergeSortRecursive(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    // Stable merge step
    private static void merge(Trade[] arr, int left, int mid, int right) {
        Trade[] temp = new Trade[right - left + 1];

        int i = left;     // left subarray
        int j = mid + 1;  // right subarray
        int k = 0;

        while (i <= mid && j <= right) {
            if (arr[i].volume <= arr[j].volume) {   // stable condition
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        // Copy back to original
        for (int t = 0; t < temp.length; t++) {
            arr[left + t] = temp[t];
        }
    }

    // ---------------------- QUICK SORT (DESCENDING) -------------------------
    public static void quickSortDesc(Trade[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(Trade[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = lomutoPartition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    // Lomuto partition for DESC sorting
    private static int lomutoPartition(Trade[] arr, int low, int high) {

        Trade pivot = arr[high]; // using last element as pivot
        int i = low - 1;

        for (int j = low; j < high; j++) {

            // Descending → larger volumes come first
            if (arr[j].volume > pivot.volume) {
                i++;
                Trade temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // place pivot correctly
        Trade temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // ---------------------- MERGE TWO SORTED LISTS -------------------------
    public static Trade[] mergeTwoSortedSessions(Trade[] morning, Trade[] afternoon) {
        int m = morning.length, n = afternoon.length;
        Trade[] merged = new Trade[m + n];

        int i = 0, j = 0, k = 0;

        while (i < m && j < n) {
            if (morning[i].volume <= afternoon[j].volume) {
                merged[k++] = morning[i++];
            } else {
                merged[k++] = afternoon[j++];
            }
        }

        while (i < m) merged[k++] = morning[i++];
        while (j < n) merged[k++] = afternoon[j++];

        return merged;
    }

    // ---------------------- TOTAL VOLUME -------------------------
    public static int computeTotalVolume(Trade[] arr) {
        int sum = 0;
        for (Trade t : arr) sum += t.volume;
        return sum;
    }

    // ----------------------------- MAIN -----------------------------
    public static void main(String[] args) {

        Trade[] trades = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        // --------- MERGE SORT ASC ---------
        Trade[] mergeArr = trades.clone();
        mergeSort(mergeArr);
        System.out.println("\nMerge Sort ASC (Stable):");
        for (Trade t : mergeArr) System.out.println("  " + t);

        // --------- QUICK SORT DESC ---------
        Trade[] quickArr = trades.clone();
        quickSortDesc(quickArr);
        System.out.println("\nQuick Sort DESC (In-place):");
        for (Trade t : quickArr) System.out.println("  " + t);

        // --------- MERGE MORNING + AFTERNOON ---------
        Trade[] morning = { new Trade("m1", 300), new Trade("m2", 600) };
        Trade[] afternoon = { new Trade("a1", 200), new Trade("a2", 400) };

        // Both should be pre-sorted before merging
        mergeSort(morning);
        mergeSort(afternoon);

        Trade[] merged = mergeTwoSortedSessions(morning, afternoon);
        System.out.println("\nMerged Morning + Afternoon:");
        for (Trade t : merged) System.out.println("  " + t);

        // --------- TOTAL VOLUME ---------
        int totalVolume = computeTotalVolume(merged);
        System.out.println("\nTotal Volume: " + totalVolume);
    }
}

