package week4;


import java.util.*;

public class Risk_Threshold {

    // ---------------- LINEAR SEARCH ----------------
    static void linearSearch(int[] arr, int target) {
        int comparisons = 0;
        boolean found = false;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                found = true;
                break;
            }
        }

        System.out.println("Linear Search:");
        System.out.println("Found: " + found);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Time Complexity: O(n)\n");
    }

    // ---------------- BINARY FLOOR ----------------
    static int floor(int[] arr, int target, int[] comp) {
        int low = 0, high = arr.length - 1;
        int ans = -1;

        while (low <= high) {
            comp[0]++;
            int mid = (low + high) / 2;

            if (arr[mid] <= target) {
                ans = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    // ---------------- BINARY CEILING --------------
    static int ceiling(int[] arr, int target, int[] comp) {
        int low = 0, high = arr.length - 1;
        int ans = -1;

        while (low <= high) {
            comp[0]++;
            int mid = (low + high) / 2;

            if (arr[mid] >= target) {
                ans = arr[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    // ------------- INSERTION POSITION -------------
    static int insertionPoint(int[] arr, int target, int[] comp) {
        int low = 0, high = arr.length;

        while (low < high) {
            comp[0]++;
            int mid = (low + high) / 2;

            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low; // lower_bound
    }

    // ---------------- MAIN ------------------------
    public static void main(String[] args) {

        int[] risks = {10, 25, 50, 100}; // sorted
        int target = 30;

        // Linear Search (unsorted case)
        linearSearch(risks, target);

        // Binary operations
        int[] comp = {0};

        int floorVal = floor(risks, target, comp);
        int ceilVal = ceiling(risks, target, comp);
        int pos = insertionPoint(risks, target, comp);

        System.out.println("Binary Search:");
        System.out.println("Floor(" + target + "): " + floorVal);
        System.out.println("Ceiling(" + target + "): " + ceilVal);
        System.out.println("Insertion Index: " + pos);
        System.out.println("Comparisons: " + comp[0]);
        System.out.println("Time Complexity: O(log n)");
    }
}


