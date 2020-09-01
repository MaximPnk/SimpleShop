package models;

import java.util.ArrayList;
import java.util.Collections;

public class Customer implements Runnable {
    private static final Shop SHOP = new Shop();
    private int id;
    private int orderCount = 0;
    private int objectCount = 0;
    private static int total = 0;
    private static ArrayList<Integer> orders = new ArrayList<>();

    public Customer(int id) {
        this.id = id;
        orders.add(id-1, orderCount);
    }

    public synchronized void buy(int objectCount) {
        if (SHOP.getObjectsCount() <= 0) {
            Thread.currentThread().interrupt();
        } else if (objectCount <= SHOP.getObjectsCount()) {
            this.objectCount += objectCount;
            SHOP.setObjectsCount(SHOP.getObjectsCount() - objectCount);
            orders.set(id-1, ++orderCount);
        } else {
            this.objectCount += SHOP.getObjectsCount();
            SHOP.setObjectsCount(0);
            orders.set(id-1, ++orderCount);
            Thread.currentThread().interrupt();
        }
        /*notifyAll();
        while (orders.get(id-1) - Collections.min(orders) > 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (SHOP) {
//                Thread.yield();
                buy((int) (Math.random() * 10 + 1));
            }
        }
        total += objectCount;
        System.out.println(this);
    }

    public static int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", orderCount=" + orderCount +
                ", objectCount=" + objectCount +
                '}';
    }
}
