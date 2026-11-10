# Checkout with the Strategy Pattern (Java)

A small Java console project, built for the final challenge of the DIO bootcamp on **Design Patterns**, that applies the **Strategy** pattern (one of the classic GoF behavioral patterns) to a checkout where the customer chooses how to pay.

## The idea

At a store's register, the order is the same, but the customer can pay with Pix, boleto or credit card, and each method changes the final total differently. Instead of filling the code with `if / else` blocks for every method, each payment method becomes its own class (a **strategy**), and the order simply asks the chosen strategy for the total.

## Payment methods

| Method | Rule | Example (R$ 150.00) |
|---|---|---|
| Pix | 5% discount | R$ 142.50 |
| Boleto | R$ 3.50 fixed fee | R$ 153.50 |
| Credit card | 4% fee | R$ 156.00 |

## How the pattern maps to the code

| Pattern role | Class | Responsibility |
|---|---|---|
| **Strategy** (interface) | `PaymentStrategy` | Defines the contract: every method must calculate its own total |
| **Concrete strategies** | `PixPayment`, `BoletoPayment`, `CreditCardPayment` | Each one implements its own rule |
| **Context** | `Order` | Holds the amount and delegates the calculation to the chosen strategy, without knowing which one it is |
| **Client** | `Main` | Lets the user pick a strategy; can swap it at runtime for the same order |

```
          <<interface>>
         PaymentStrategy
   + getName()  + getRule()
   + calculateTotal(amount)
        ^        ^        ^
        |        |        |
 PixPayment  BoletoPayment  CreditCardPayment

 Order ---- uses ----> PaymentStrategy
```

## Why this pattern is useful

- **No giant if/else:** adding a method does not mean editing a long conditional.
- **Open/Closed Principle:** the code is open for extension (new strategies) and closed for modification (`Order` never changes).
- **Swappable at runtime:** the same order can be recalculated with another strategy (see the "Compare all methods" option).
- **Easy to test:** each rule lives in a small class of its own.

## Adding a new payment method

1. Create a class that implements `PaymentStrategy`, for example `DebitCardPayment`.
2. Register it with a single line in the `METHODS` map in `Main.java`:

```java
METHODS.put(4, new DebitCardPayment());
```

The menu is built from the map, so nothing else needs to change.

## Other concepts used

- **BigDecimal** with `RoundingMode.HALF_UP` for exact money calculations.
- **Map** (`LinkedHashMap`) as an ordered menu of strategies.
- **Input validation** with clear messages for invalid amounts and options.

## Project structure

```
StrategyPayment/
├── src/
│   ├── PaymentStrategy.java     # Strategy interface
│   ├── PixPayment.java          # concrete strategy
│   ├── BoletoPayment.java       # concrete strategy
│   ├── CreditCardPayment.java   # concrete strategy
│   ├── Order.java               # context
│   └── Main.java                # console program
└── .gitignore
```

## How to run

**Requirements:** JDK 8 or higher.

### Using IntelliJ IDEA

1. Open the project folder in IntelliJ.
2. Open `src/Main.java`.
3. Click the green run button next to `main`.

### Using the command line

From the project root folder:

```
javac -d out src/PaymentStrategy.java src/PixPayment.java src/BoletoPayment.java src/CreditCardPayment.java src/Order.java src/Main.java
java -cp out Main
```

## Example session

```
=== Checkout (Strategy Pattern) ===

Enter the order amount (e.g. 150.00):
200,50
Choose the payment method:
  1) Pix (5% discount)
  2) Boleto (R$ 3.50 fixed fee)
  3) Credit card (4% fee)
  4) Compare all methods
4

Original amount: R$ 200.50
Pix (5% discount) -> total: R$ 190.48
Boleto (R$ 3.50 fixed fee) -> total: R$ 204.00
Credit card (4% fee) -> total: R$ 208.52

Another order? (y/n)
n
Goodbye, Sandro!
```

## Possible next steps

- Add installments (with interest) to the credit card strategy.
- Combine with the **Factory** pattern to create strategies by name.
- Rebuild the project with **Spring**, injecting the strategies as beans.

## Author

Sandro ([@Sandro-ia](https://github.com/Sandro-ia))
