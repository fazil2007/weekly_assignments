import java.util.*;

class FlashSaleManager {

    HashMap<String, Integer> stock = new HashMap<>();
    HashMap<String, LinkedList<Integer>> waitingList = new HashMap<>();

    public FlashSaleManager() {
        stock.put("IPHONE15_256GB", 100);
        waitingList.put("IPHONE15_256GB", new LinkedList<>());
    }

    public void purchaseItem(String productId, int userId) {

        int currentStock = stock.getOrDefault(productId, 0);

        if (currentStock > 0) {
            stock.put(productId, currentStock - 1);
            System.out.println("Success, " + (currentStock - 1) + " units remaining");
        }
        else {
            LinkedList<Integer> queue = waitingList.get(productId);
            queue.add(userId);
            System.out.println("Added to waiting list, position #" + queue.size());
        }
    }

    public void checkStock(String productId) {
        System.out.println(productId + " → " + stock.getOrDefault(productId,0) + " units available");
    }
}

public class flashsale {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        FlashSaleManager manager = new FlashSaleManager();

        System.out.println("Flash Sale System Started");
        System.out.println("Type 'close' to stop\n");

        while (true) {

            System.out.print("Enter product ID: ");
            String productId = sc.nextLine();

            if (productId.equalsIgnoreCase("close")) {
                System.out.println("System Closed");
                break;
            }

            manager.checkStock(productId);

            System.out.print("Enter user ID: ");
            int userId = Integer.parseInt(sc.nextLine());

            manager.purchaseItem(productId, userId);
        }

        sc.close();
    }
}