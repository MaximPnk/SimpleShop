package models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;

@Getter
@Setter
public class Shop {
    private int objectsCount = 1000;
    private ArrayList<Integer> orders = new ArrayList<>();
    private int total = 0;
    private static Shop instance;

    private Shop() {
    }

    public static Shop getInstance() {
        if (instance == null) {
            instance = new Shop();
        }
        return instance;
    }

    public synchronized void buy(Customer customer, int count) {
        if (objectsCount <= 0) {
            Thread.currentThread().interrupt();
            return;
        } else if (count <= objectsCount) {
            customer.setObjectCount(customer.getObjectCount() + count);
            objectsCount -= count;
            customer.setOrderCount(customer.getOrderCount() + 1);
            orders.set(customer.getId() - 1, customer.getOrderCount());
        } else {
            customer.setObjectCount(customer.getObjectCount() + objectsCount);
            objectsCount = 0;
            customer.setOrderCount(customer.getOrderCount() + 1);
            orders.set(customer.getId() - 1, customer.getOrderCount());
            Thread.currentThread().interrupt();
            return;
        }
        notifyAll();
        while (orders.get(customer.getId()-1) - Collections.min(orders) > 1) {
            try {
                wait(100);
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
