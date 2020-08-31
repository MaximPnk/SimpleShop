import models.Customer;

public class Main {

    public static void main(String[] args) {

        if (checkArgsLength(args)) {
            int threads = Integer.parseInt(args[0]);
            for (int i = 0; i < threads; i++) {
                new Thread(new Customer(i+1)).start();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Total = " + Customer.getTotal());
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
