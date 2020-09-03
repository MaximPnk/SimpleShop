package models;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Callable;

@Getter
@Setter
public class Customer implements Callable<Integer> {

    private static final Stock STOCK = Stock.getInstance();
    private int id;
    private int orderCount = 0;
    private int objectCount = 0;

    public Customer(int id) {
        this.id = id;
        STOCK.getOrders().add(id-1, orderCount);
    }

    @Override
    public Integer call() throws Exception {
        while (!Thread.currentThread().isInterrupted()) {
            Thread.yield();
            STOCK.buy(this, (int) (Math.random() * 10 + 1));
        }
        System.out.println(this);
        return objectCount;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", orderCount=" + orderCount +
                ", objectCount=" + objectCount +
                '}';
    }

    /*public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Thread.yield();
            SHOP.buy(this, (int) (Math.random() * 10 + 1));
        }
        SHOP.setTotal(SHOP.getTotal() + objectCount);
        System.out.println(this);
    }*/
}
