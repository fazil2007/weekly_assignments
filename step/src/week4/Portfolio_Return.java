package week4;


import java.util.*;

class Asset {
    String name;
    double returnRate;
    double volatility;

    Asset(String name, double returnRate, double volatility) {
        this.name = name;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    public String toString() {
        return name + ":" + returnRate + "% (vol=" + volatility + ")";
    }
}

public class Portfolio_Return {

    // ---------------- MERGE SORT (STABLE) ----------------
    static void mergeSort(List<Asset> arr, int left, int right) {
        if (left >= right) return;

        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    static void merge(List<Asset> arr, int left, int mid, int right) {
        List<Asset> temp = new ArrayList<>();

        int i = left, j = mid + 1;

        while (i <= mid && j <= right) {
            if (arr.get(i).returnRate <= arr.get(j).returnRate) {
                temp.add(arr.get(i++)); // stable
            } else {
                temp.add(arr.get(j++));
            }
        }

        while (i <= mid) temp.add(arr.get(i++));
        while (j <= right) temp.add(arr.get(j++));

        for (int k = 0; k < temp.size(); k++) {
            arr.set(left + k, temp.get(k));
        }
    }

    // ---------------- QUICK SORT ----------------
    static void quickSort(List<Asset> arr, int low, int high) {
        if (high - low <= 10) { // hybrid optimization
            insertionSort(arr, low, high);
            return;
        }

        if (low < high) {
            int pivotIndex = medianOf3(arr, low, high);
            int pi = partition(arr, low, high, pivotIndex);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    static int partition(List<Asset> arr, int low, int high, int pivotIndex) {
        Asset pivot = arr.get(pivotIndex);
        Collections.swap(arr, pivotIndex, high);

        int i = low;

        for (int j = low; j < high; j++) {
            if (compare(arr.get(j), pivot) < 0) {
                Collections.swap(arr, i, j);
                i++;
            }
        }

        Collections.swap(arr, i, high);
        return i;
    }

    // DESC by returnRate, if tie → ASC volatility
    static int compare(Asset a, Asset b) {
        if (a.returnRate != b.returnRate)
            return Double.compare(b.returnRate, a.returnRate);
        return Double.compare(a.volatility, b.volatility);
    }

    // ----------- MEDIAN OF 3 PIVOT ---------------
    static int medianOf3(List<Asset> arr, int low, int high) {
        int mid = (low + high) / 2;

        Asset a = arr.get(low);
        Asset b = arr.get(mid);
        Asset c = arr.get(high);

        if (compare(a, b) < 0) {
            if (compare(b, c) < 0) return mid;
            else if (compare(a, c) < 0) return high;
            else return low;
        } else {
            if (compare(a, c) < 0) return low;
            else if (compare(b, c) < 0) return high;
            else return mid;
        }
    }

    // -------- INSERTION SORT (HYBRID) ------------
    static void insertionSort(List<Asset> arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Asset key = arr.get(i);
            int j = i - 1;

            while (j >= low && compare(arr.get(j), key) > 0) {
                arr.set(j + 1, arr.get(j));
                j--;
            }
            arr.set(j + 1, key);
        }
    }

    // -------------------- MAIN -------------------
    public static void main(String[] args) {

        List<Asset> assets = new ArrayList<>();
        assets.add(new Asset("AAPL", 12, 5));
        assets.add(new Asset("TSLA", 8, 9));
        assets.add(new Asset("GOOG", 15, 4));

        // Merge Sort (ascending)
        List<Asset> mergeList = new ArrayList<>(assets);
        mergeSort(mergeList, 0, mergeList.size() - 1);
        System.out.println("Merge Sort (ASC): " + mergeList);

        // Quick Sort (DESC + volatility ASC)
        List<Asset> quickList = new ArrayList<>(assets);
        quickSort(quickList, 0, quickList.size() - 1);
        System.out.println("Quick Sort (DESC + VOL): " + quickList);
    }
}


