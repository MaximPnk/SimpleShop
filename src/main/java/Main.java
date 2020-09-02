import models.Customer;
import models.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        if (checkArgsLength(args)) {

            /*for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                new Thread(new Customer(i+1)).start();
            }*/

            ExecutorService es = Executors.newFixedThreadPool(Integer.parseInt(args[0]));
            int sum = 0;
            List<Future<Integer>> list = new ArrayList<>();
            for (int i = 0; i < Integer.parseInt(args[0]); i++) {
                list.add(es.submit(new Customer(i+1)));
            }
            es.shutdown();

            for (Future<Integer> integerFuture : list) {
                try {
                    sum += integerFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Total = " + sum);
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
