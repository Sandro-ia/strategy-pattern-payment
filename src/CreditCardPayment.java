import java.math.BigDecimal;
import java. math.RoundingMode;

/**
 * CONCRETE STRATEGY #3: Credit card adds a 4% fee on top of the amount.
 */
public class CreditCardPayment implements PaymentStrategy{

    private static final BigDecimal MULTIPLIER = new BigDecimal("1.04"); // 100% + 4%

    @Override
    public String getName() {
        return "Credit Card";
    }

    @Override
    public String getRule() {
        return "4% fee";
    }

    @Override
    public BigDecimal calculateTotal(BigDecimal amount) {
        return amount.multiply(MULTIPLIER).setScale(2, RoundingMode.HALF_UP);
    }
}
