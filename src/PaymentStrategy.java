import java.math.BigDecimal;

/**
 * THE STRATEGY (the "contract").
 *
 * ANALOGY: this is the instruction on the card machine: "tell me the final
 * amount to charge". It does not say HOW each payment method calculates
 * it; it only says that every method MUST be able to answer this question.
 *
 * Each payment method (Pix, Boleto, Credit Card...) is a concrete strategy:
 * a class that implements this interface with its own rule.
 */
public interface PaymentStrategy {

    // Name shown to the user (e.g. "Pix").
    String getName();

    // Short description of the rule (e.g. "5% discount").
    String getRule();

    // The heart of the pattern: each strategy calculates the final total
    // in its own way, from the original amount.
    BigDecimal calculateTotal(BigDecimal amount);
}
