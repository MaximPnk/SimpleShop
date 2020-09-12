import models.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {


    public static void main(String[] args) {

        if (checkArgsLength(args)) {

            Customer.setPhaser(new Phaser(Integer.parseInt(args[0])));

            for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                new Thread(new Customer(i+1)).start();
            }

            while (Thread.activeCount() != 2) {}
            System.out.println(Customer.getTotal());
        }
    }

    public static boolean checkArgsLength(String[] args) {
        if (args.length != 1) {
            System.out.println("Запуск программы происходит со следующими параметрами: \"Количество покупателей\"");
            return false;
        }
        try {
            int number = Integer.parseInt(args[0]);
            if (number < 1) {
                System.out.println("Запуск программы происходит со следующими параметрами: \"Количество покупателей\"");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Запуск программы происходит со следующими параметрами: \"Количество покупателей\"");
            return false;
        }
        return true;
    }


}
