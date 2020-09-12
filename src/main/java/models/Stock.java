package models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stock {
    private int objectsCount = 1000000;
    private static Stock instance;

    private Stock() {
    }

    public static Stock getInstance() {
        if (instance == null) {
            instance = new Stock();
        }
        return instance;
    }

    public synchronized int sell(int count) {
        if (objectsCount <= 0) {
            return 0;
        } else if (count <= objectsCount) {
            objectsCount -= count;
            return count;
        } else {
            count = objectsCount;
            objectsCount = 0;
            return count;
        }
    }
}
