import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * CONCRETE STRATEGY #2: Boleto adds a FIXED fee of R$ 3.50.
 *
 * Same contract as Pix, completely different rule. That is the whole idea
 * of the pattern: same question ("what is the total?"), different answers.
 */
public class BoletoPayment implements PaymentStrategy {

    private static final BigDecimal FEE = new BigDecimal("3.50");

    @Override
    public String getName() {
        return "Boleto";
    }

    @Override
    public String getRule() {
        return "R$ 3.50 fixed fee";
    }

    @Override
    public BigDecimal calculateTotal(BigDecimal amount) {
        return amount.add(FEE).setScale(2,RoundingMode.HALF_UP);
    }
}
