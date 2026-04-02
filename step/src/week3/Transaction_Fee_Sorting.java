package week3;

import java.util.*;

class Transaction {
    String id;
    double fee;
    String timestamp; // HH:MM format

    public Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return id + ": " + fee + " @ " + timestamp;
    }
}

public class Transaction_Fee_Sorting {

    // ------------------ BUBBLE SORT (≤100) --------------------
    public static void bubbleSort(ArrayList<Transaction> list) {
        int n = list.size();

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                }
            }

            // Early exit if already sorted
            if (!swapped)
                break;
        }
    }

    // --------- Compare two timestamps in HH:MM format ----------
    private static boolean isGreaterTime(String t1, String t2) {
        return t1.compareTo(t2) > 0;
    }

    // ---------- INSERTION SORT (100–1000) ---------------------
    public static void insertionSort(ArrayList<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            // Compare by fee, then timestamp for stability
            while (j >= 0 &&
                    (list.get(j).fee > key.fee ||
                            (list.get(j).fee == key.fee &&
                                    isGreaterTime(list.get(j).timestamp, key.timestamp)))) {

                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }
    }

    // ------------------ OUTLIER CHECK ------------------------
    public static void detectHighFee(ArrayList<Transaction> list) {
        System.out.println("\nHigh-Fee Outliers (> $50):");
        boolean found = false;

        for (Transaction t : list) {
            if (t.fee > 50) {
                System.out.println("  OUTLIER -> " + t.id + " : $" + t.fee);
                found = true;
            }
        }

        if (!found) {
            System.out.println("  None");
        }
    }

    // ------------------ MAIN DRIVER ---------------------------
    public static void main(String[] args) {

        ArrayList<Transaction> list = new ArrayList<>();
        list.add(new Transaction("id1", 10.5, "10:00"));
        list.add(new Transaction("id2", 25.0, "09:30"));
        list.add(new Transaction("id3", 5.0, "10:15"));
        // you can add more transactions here...

        System.out.println("Original Transactions:");
        for (Transaction t : list) {
            System.out.println("  " + t);
        }

        // Choose sorting algorithm based on batch size
        if (list.size() <= 100) {
            bubbleSort(list);
            System.out.println("\nUsing Bubble Sort (≤100)");
        } else if (list.size() <= 1000) {
            insertionSort(list);
            System.out.println("\nUsing Insertion Sort (100–1000)");
        }

        // Print sorted output
        System.out.println("\nSorted Transactions:");
        for (Transaction t : list) {
            System.out.println("  " + t);
        }

        // Detect high-fee outliers
        detectHighFee(list);
    }
}

