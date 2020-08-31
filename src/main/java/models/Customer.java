package models;

public class Customer implements Runnable {
    private int id;
    private int orderCount = 0;
    private int objectCount = 0;
    private static int total = 0;
    private static final Shop SHOP = new Shop();

    public Customer(int id) {
        this.id = id;
    }

    public void buy(int objectCount) {
        if (SHOP.getObjectsCount() <= 0) {
            Thread.currentThread().interrupt();
        } else if (objectCount <= SHOP.getObjectsCount()) {
            this.objectCount += objectCount;
            SHOP.setObjectsCount(SHOP.getObjectsCount() - objectCount);
            orderCount++;
        } else {
            this.objectCount += SHOP.getObjectsCount();
            SHOP.setObjectsCount(0);
            orderCount++;
            Thread.currentThread().interrupt();
        }
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (SHOP) {
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
