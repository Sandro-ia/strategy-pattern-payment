
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // Menu: option number -> strategy. LinkedHashMap keeps the insertion
    // order, so the menu always appears as 1, 2, 3...
    //
    // TO ADD A NEW PAYMENT METHOD: create a class that implements
    // PaymentStrategy and register it here with ONE new line.
    // Order and the rest of this file do not need to change.
    private static final Map<Integer, PaymentStrategy> METHODS = new LinkedHashMap<>();

    static {
        METHODS.put(1, new PixPayment());
        METHODS.put(2, new BoletoPayment());
        METHODS.put(3, new CreditCardPayment());
    }

    private static final int COMPARE_ALL = METHODS.size() + 1;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepGoing = true;

        System.out.println("======= Checkout (Strategy Patter) =======");

        while (keepGoing) {
            System.out.println();
            BigDecimal amount = readAmount(scanner);
            Order order = new Order(amount);

            int choice = readMethodChoice(scanner);

            if (choice == COMPARE_ALL) {
                System.out.println();
                System.out.println("Original amount: R$ " + order.getAmount().toPlainString());
                for (PaymentStrategy strategy : METHODS.values()) {
                    // The SAME order, with the strategy swapped at runtime.
                    order.setPaymentStrategy(strategy);
                    printResult(order);
                }
            } else {
                order.setPaymentStrategy(METHODS.get(choice));
                System.out.println();
                System.out.println("Original amount: R$ " + order.getAmount().toPlainString());
                printResult(order);
            }

            System.out.println();
            System.out.println("Another order? (y/n)");
            keepGoing = scanner.nextLine().trim().equalsIgnoreCase("y");
        }

        System.out.println("Goodbye, Sandro!");
        scanner.close();
    }

    private static void printResult(Order order) {
        PaymentStrategy strategy = order.getPaymentStrategy();
        System.out.println(strategy.getName() + " (" + strategy.getRule() + ") -> total: R$ " + order.checkout().toPlainString());
    }

    private static BigDecimal readAmount(Scanner scanner) {
        while (true) {
            System.out.println("Enter the order amount (e.g. 150.00):");
            // Accepts comma or dot as the decimal separator.
            String input = scanner.nextLine().trim().replace(',','.');
            try {
                BigDecimal amount = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
                if (amount.signum() > 0) {
                    return amount;
                }

                System.out.println("The amount must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private static int readMethodChoice(Scanner scanner) {
        while (true) {
            System.out.println("Choose the payment method:");
            for (Map.Entry<Integer, PaymentStrategy> entry : METHODS.entrySet()) {
                PaymentStrategy strategy = entry.getValue();
                System.out.println(" " + entry.getKey() + ") " + strategy.getName() + " (" + strategy.getRule() + ")");
            }

            System.out.println(" " + COMPARE_ALL + ") Compare all methods");

            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (METHODS.containsKey(choice) || choice == COMPARE_ALL) {
                    return choice;
                }
                System.out.println("Please choose a number from  the menu.");
            } catch (NumberFormatException e) {
                System.out.println("Please type a number from the menu.");
            }
        }
    }
}