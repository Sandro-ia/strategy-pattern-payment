import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CONCRETE STRATEGY #1: Pix pays 5% LESS than the original amount.
 *
 * BigDecimal is used for money (instead of double) to avoid tiny rounding
 * errors, as we practiced in the calculator project. HALF_UP is the usual
 * "round 0.5 up" rule we learn at school.
 */
public class PixPayment implements PaymentStrategy {

    private static final BigDecimal MULTIPLIER = new BigDecimal("0.95"); // 100% - 5%

    @Override
    public String getName() {
        return "Pix";
    }

    @Override
    public String getRule() {
        return "5% discount";
    }

    @Override
    public BigDecimal calculateTotal(BigDecimal amount) {
        return amount.multiply(MULTIPLIER).setScale(2, RoundingMode.HALF_UP);
    }
}
