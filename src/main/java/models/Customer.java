package models;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Phaser;

@Getter
@Setter
public class Customer implements Runnable {
    private static final Stock STOCK = Stock.getInstance();
    private static Phaser phaser;
    private static int total = 0;
    private int id;
    private int orderCount = 0;
    private int objectCount = 0;

    public Customer(int id) {
        this.id = id;

    }

    public void buy(int count) {
        phaser.arriveAndAwaitAdvance();
        int sellCount = STOCK.sell(count);
        if (sellCount == count) {
            orderCount++;
            objectCount += sellCount;
        } else if (sellCount == 0) {
            phaser.arriveAndDeregister();
            Thread.currentThread().interrupt();
        } else {
            orderCount++;
            objectCount += sellCount;
            phaser.arriveAndDeregister();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            buy((int) (Math.random() * 10 + 1));
        }
        System.out.println(this);
        total += objectCount;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", orderCount=" + orderCount +
                ", objectCount=" + objectCount +
                '}';
    }

    public static void setPhaser(Phaser phaser) {
        Customer.phaser = phaser;
    }

    public static int getTotal() {
        return total;
    }
}
