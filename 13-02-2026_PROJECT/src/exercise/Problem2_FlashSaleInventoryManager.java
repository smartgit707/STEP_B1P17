package exercise;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Problem2_FlashSaleInventoryManager {

    private final ConcurrentHashMap<String, AtomicInteger> stock = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Integer>> waitingList = new ConcurrentHashMap<>();

    public void addProduct(String productId, int quantity) {
        stock.put(productId, new AtomicInteger(quantity));
        waitingList.put(productId, new ConcurrentLinkedQueue<>());
    }

    public int checkStock(String productId) {
        return stock.get(productId).get();
    }

    public String purchaseItem(String productId, int userId) {
        AtomicInteger currentStock = stock.get(productId);

        if (currentStock.get() > 0) {
            int remaining = currentStock.decrementAndGet();
            return "Success, " + remaining + " units remaining";
        } else {
            waitingList.get(productId).add(userId);
            return "Added to waiting list, position #" + waitingList.get(productId).size();
        }
    }

    public static void solve() {
        Problem2_FlashSaleInventoryManager manager = new Problem2_FlashSaleInventoryManager();

        manager.addProduct("P1", 2);

        System.out.println(manager.purchaseItem("P1", 101));
        System.out.println(manager.purchaseItem("P1", 102));
        System.out.println(manager.purchaseItem("P1", 103));
    }
}
