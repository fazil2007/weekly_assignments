package week4;


import java.util.*;

public class Account_ID_Lookup {

    // ---------------- LINEAR SEARCH ----------------
    static void linearSearch(String[] arr, String target) {
        int first = -1, last = -1;
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                if (first == -1) first = i;
                last = i;
            }
        }

        System.out.println("Linear Search:");
        System.out.println("First Occurrence: " + first);
        System.out.println("Last Occurrence: " + last);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Time Complexity: O(n)\n");
    }

    // ---------------- BINARY SEARCH (FIRST) --------
    static int binaryFirst(String[] arr, String target, int[] comp) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            comp[0]++;
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                high = mid - 1; // move left
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    // ---------------- BINARY SEARCH (LAST) ---------
    static int binaryLast(String[] arr, String target, int[] comp) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            comp[0]++;
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                low = mid + 1; // move right
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    // ---------------- BINARY SEARCH WRAPPER --------
    static void binarySearch(String[] arr, String target) {
        int[] comp = {0};

        int first = binaryFirst(arr, target, comp);
        int last = binaryLast(arr, target, comp);

        int count = (first == -1) ? 0 : (last - first + 1);

        System.out.println("Binary Search:");
        System.out.println("First Occurrence: " + first);
        System.out.println("Last Occurrence: " + last);
        System.out.println("Count: " + count);
        System.out.println("Comparisons: " + comp[0]);
        System.out.println("Time Complexity: O(log n)\n");
    }

    // ---------------- MAIN -------------------------
    public static void main(String[] args) {

        String[] logs = {"accB", "accA", "accB", "accC"};

        // Linear Search (unsorted allowed)
        linearSearch(logs, "accB");

        // Sort for Binary Search
        Arrays.sort(logs);
        System.out.println("Sorted Logs: " + Arrays.toString(logs));

        // Binary Search
        binarySearch(logs, "accB");
    }
}

