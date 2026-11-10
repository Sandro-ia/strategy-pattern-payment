import java.math.BigDecimal;

/**
 * THE CONTEXT: the class that USES a strategy without knowing which one.
 *
 * ANALOGY: the cashier. The cashier holds the order and asks the card
 * machine for the total. Whether the customer chose Pix, boleto or card,
 * the cashier does exactly the same thing: ask "what is the total?".
 *
 * Notice that Order never mentions Pix, Boleto or CreditCard. It only
 * knows the PaymentStrategy interface. That is why you can add a new
 * payment method later WITHOUT touching this class.
 */
public class Order {

    private final BigDecimal amount;
    private PaymentStrategy paymentStrategy;

    public Order(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("The order amount must be greater than zero.");
        }
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // The strategy can be swapped at any moment, even for the same order:
    // like the customer changing their mind at the register.
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public BigDecimal checkout() {
        if (paymentStrategy == null) {
            throw new IllegalArgumentException("Choose a payment method first.");
        }

        // Delegation: the Order hands the calculation over to the strategy.
        return paymentStrategy.calculateTotal(amount);
    }
}
