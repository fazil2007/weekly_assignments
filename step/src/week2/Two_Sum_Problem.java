package week2;

import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    String account;
    int time; // in minutes

    Transaction(int id, int amount, String merchant, String account, int time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.account = account;
        this.time = time;
    }
}

public class Two_Sum_Problem {

    // ------------------ TWO SUM ------------------
    static List<int[]> twoSum(List<Transaction> t, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        List<int[]> res = new ArrayList<>();

        for (Transaction tx : t) {
            int comp = target - tx.amount;
            if (map.containsKey(comp)) {
                res.add(new int[]{map.get(comp), tx.id});
            }
            map.put(tx.amount, tx.id);
        }
        return res;
    }

    // -------- TWO SUM WITH TIME WINDOW ----------
    static List<int[]> twoSumTime(List<Transaction> t, int target) {
        Map<Integer, List<Transaction>> map = new HashMap<>();
        List<int[]> res = new ArrayList<>();

        for (Transaction tx : t) {
            int comp = target - tx.amount;

            if (map.containsKey(comp)) {
                for (Transaction prev : map.get(comp)) {
                    if (Math.abs(tx.time - prev.time) <= 60) {
                        res.add(new int[]{prev.id, tx.id});
                    }
                }
            }
            map.computeIfAbsent(tx.amount, k -> new ArrayList<>()).add(tx);
        }
        return res;
    }

    // ------------------ K SUM -------------------
    static void kSumHelper(List<Integer> nums, int target, int k, int start,
                           List<Integer> path, List<List<Integer>> res) {

        if (k == 2) {
            Set<Integer> set = new HashSet<>();
            for (int i = start; i < nums.size(); i++) {
                if (set.contains(target - nums.get(i))) {
                    path.add(nums.get(i));
                    res.add(new ArrayList<>(path));
                    path.remove(path.size() - 1);
                }
                set.add(nums.get(i));
            }
            return;
        }

        for (int i = start; i < nums.size(); i++) {
            path.add(nums.get(i));
            kSumHelper(nums, target - nums.get(i), k - 1, i + 1, path, res);
            path.remove(path.size() - 1);
        }
    }

    static List<List<Integer>> kSum(List<Transaction> t, int target, int k) {
        List<Integer> nums = new ArrayList<>();
        for (Transaction tx : t) nums.add(tx.amount);

        List<List<Integer>> res = new ArrayList<>();
        kSumHelper(nums, target, k, 0, new ArrayList<>(), res);

        return res;
    }

    // ------------ DUPLICATE DETECTION ----------
    static void detectDuplicates(List<Transaction> t) {
        Map<String, Set<String>> map = new HashMap<>();

        for (Transaction tx : t) {
            String key = tx.amount + "-" + tx.merchant;
            map.computeIfAbsent(key, k -> new HashSet<>()).add(tx.account);
        }

        System.out.println("\nDuplicate Transactions:");
        for (String key : map.keySet()) {
            if (map.get(key).size() > 1) {
                System.out.println("Duplicate found: " + key);
            }
        }
    }

    // ------------------ MAIN -------------------
    public static void main(String[] args) {

        List<Transaction> t = Arrays.asList(
                new Transaction(1, 500, "StoreA", "acc1", 600),
                new Transaction(2, 300, "StoreB", "acc2", 615),
                new Transaction(3, 200, "StoreC", "acc3", 630),
                new Transaction(4, 500, "StoreA", "acc4", 650)
        );

        // Two Sum
        System.out.println("Two Sum:");
        for (int[] p : twoSum(t, 500)) {
            System.out.println(p[0] + " , " + p[1]);
        }

        // Two Sum with Time Window
        System.out.println("\nTwo Sum (Time Window):");
        for (int[] p : twoSumTime(t, 500)) {
            System.out.println(p[0] + " , " + p[1]);
        }

        // K Sum
        System.out.println("\nK Sum:");
        for (List<Integer> list : kSum(t, 1000, 3)) {
            System.out.println(list);
        }

        // Duplicate Detection
        detectDuplicates(t);
    }
}

