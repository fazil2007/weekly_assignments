package week3;

class Client {
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
        return name + "(" + riskScore + ", $" + accountBalance + ")";
    }
}

public class Client_Rist_Score {

    // -------------------- BUBBLE SORT (ASC) --------------------
    // Also prints each swap for visualization
    public static void bubbleSortAsc(Client[] arr) {
        int n = arr.length;
        int swapCount = 0;

        System.out.println("\nBubble Sort (Ascending) — Swap Visualization:");

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {

                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    // Print swap visualization
                    System.out.println("Swap: " + arr[j] + " <--> " + arr[j + 1]);

                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    swapped = true;
                    swapCount++;
                }
            }

            if (!swapped) break;
        }

        System.out.println("Total Swaps = " + swapCount);
    }

    // ----------------- INSERTION SORT (DESC + BALANCE) -----------------
    // Primary: riskScore DESC
    // Secondary: accountBalance ASC
    public static void insertionSortDesc(Client[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            Client key = arr[i];
            int j = i - 1;

            while (j >= 0 &&
                    // Higher riskScore should come first
                    (arr[j].riskScore < key.riskScore ||

                            // If same riskScore → sort by accountBalance ascending
                            (arr[j].riskScore == key.riskScore &&
                                    arr[j].accountBalance > key.accountBalance))) {

                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    // -------------------- TOP 10 HIGHEST RISK --------------------
    public static void printTopClients(Client[] arr, int topN) {
        System.out.println("\nTop " + topN + " Highest-Risk Clients:");

        for (int i = 0; i < Math.min(topN, arr.length); i++) {
            System.out.println("  " + arr[i].name + " (Risk=" + arr[i].riskScore + ")");
        }
    }

    // --------------------------- MAIN ---------------------------
    public static void main(String[] args) {

        Client[] clients = {
                new Client("clientC", 80, 4000),
                new Client("clientA", 20, 5000),
                new Client("clientB", 50, 2000)
        };

        // ----------- Bubble Sort (ASC) -----------
        Client[] bubbleArr = clients.clone();
        bubbleSortAsc(bubbleArr);

        System.out.println("\nAfter Bubble Sort (ASC):");
        for (Client c : bubbleArr) System.out.println("  " + c);

        // ----------- Insertion Sort (DESC) -----------
        Client[] insertionArr = clients.clone();
        insertionSortDesc(insertionArr);

        System.out.println("\nAfter Insertion Sort (DESC + Balance):");
        for (Client c : insertionArr) System.out.println("  " + c);

        // ----------- Top N High-Risk -----------
        printTopClients(insertionArr, 3);  // for sample input
    }
}

