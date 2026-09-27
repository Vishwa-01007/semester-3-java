abstract class PaymentMethod {

    private static int counter = 1000;

    private final String transactionId;

    public PaymentMethod() {
        counter++;
        transactionId = "TXN-" + counter;
    }

    public abstract String processPayment(double amount);

    public String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }

    public String getTransactionId() {
        return transactionId;
    }
}

class CreditCardPayment extends PaymentMethod {

    private String cardNumberLastFour;

    public CreditCardPayment(String cardNumberLastFour) {
        this.cardNumberLastFour = cardNumberLastFour;
    }

    @Override
    public String processPayment(double amount) {
        return "Charged $" + amount +
                " to card ending " + cardNumberLastFour +
                " - Txn " + getTransactionId();
    }
}

class CashPayment extends PaymentMethod {

    public CashPayment() {
        super();
    }

    @Override
    public String processPayment(double amount) {
        return "Received $" + amount +
                " in cash - Txn " + getTransactionId();
    }
}

public class Problem1Checkout {

    static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    static void testUpcasting() {

        CreditCardPayment cc = new CreditCardPayment("4471");

        // Upcasting: CreditCardPayment reference stored
        // in a PaymentMethod reference.
        PaymentMethod ref = cc;

        printConfirmation(ref, 250.0);
    }

    public static void main(String[] args) {

        CreditCardPayment cc =
                new CreditCardPayment("4471");

        System.out.println(cc.processPayment(250.0));

        System.out.println(
                cc.processPayment(250.0, "Birthday gift")
        );

        CashPayment cash = new CashPayment();

        System.out.println(cash.processPayment(40.0));

        testUpcasting();

        // PaymentMethod p = new PaymentMethod();
        // This will NOT compile because PaymentMethod is abstract.
    }
}