import java.util.ArrayList;
import java.util.List;

// ========================================
// Shipping Type Interface
// ========================================
interface ShippingType {

    double calculateCharge(double weight);

    String getTypeName();
}

// ========================================
// Standard Shipping
// ₹40 + ₹10 per kg
// ========================================
class StandardShipping implements ShippingType {

    @Override
    public double calculateCharge(double weight) {
        return 40 + (10 * weight);
    }

    @Override
    public String getTypeName() {
        return "Standard";
    }
}

// ========================================
// Express Shipping
// ₹80 + ₹15 per kg
// ========================================
class ExpressShipping implements ShippingType {

    @Override
    public double calculateCharge(double weight) {
        return 80 + (15 * weight);
    }

    @Override
    public String getTypeName() {
        return "Express";
    }
}

// ========================================
// Fragile Shipping
// Standard rate + ₹50
// ========================================
class FragileShipping implements ShippingType {

    private final StandardShipping standardShipping;

    public FragileShipping() {
        standardShipping = new StandardShipping();
    }

    @Override
    public double calculateCharge(double weight) {
        return standardShipping.calculateCharge(weight) + 50;
    }

    @Override
    public String getTypeName() {
        return "Fragile";
    }
}

// ========================================
// Notification Channel Interface
// ========================================
interface NotificationChannel {

    void notify(String parcelId, Parcel.Status status);
}

// ========================================
// SMS Channel
// ========================================
class SmsChannel implements NotificationChannel {

    @Override
    public void notify(
            String parcelId,
            Parcel.Status status
    ) {

        System.out.println(
                "[SMS] " + parcelId
                        + " is now " + status
        );
    }
}

// ========================================
// Email Channel
// ========================================
class EmailChannel implements NotificationChannel {

    @Override
    public void notify(
            String parcelId,
            Parcel.Status status
    ) {

        System.out.println(
                "[Email] " + parcelId
                        + " is now " + status
        );
    }
}

// ========================================
// Customer
// ========================================
class Customer {

    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// ========================================
// Parcel
// ========================================
class Parcel {

    // ------------------------------------
    // Status
    // ------------------------------------
    enum Status {
        BOOKED,
        PICKED_UP,
        IN_TRANSIT,
        OUT_FOR_DELIVERY,
        DELIVERED,
        CANCELLED
    }

    private String parcelId;
    private double weight;
    private ShippingType shippingType;
    private Status status;
    private Customer customer;

    private List<NotificationChannel> channels;

    public Parcel(
            String parcelId,
            double weight,
            ShippingType shippingType,
            Customer customer
    ) {

        if (weight <= 0) {
            throw new IllegalArgumentException(
                    "Weight must be positive."
            );
        }

        this.parcelId = parcelId;
        this.weight = weight;
        this.shippingType = shippingType;
        this.customer = customer;

        this.status = null;

        this.channels = new ArrayList<>();
    }

    // ====================================
    // Book Parcel
    // ====================================
    public void book() {

        if (status != null) {
            System.out.println(
                    "Parcel " + parcelId
                            + " is already booked."
            );
            return;
        }

        status = Status.BOOKED;

        System.out.printf(
                "Parcel %s booked (%s, %.0f kg).%n",
                parcelId,
                shippingType.getTypeName(),
                weight
        );

        System.out.printf(
                "Charge: ₹%.2f%n",
                shippingType.calculateCharge(weight)
        );

        notifyChannels();
    }

    // ====================================
    // Subscribe Notification
    // ====================================
    public void subscribe(
            NotificationChannel channel
    ) {

        if (channel != null &&
                !channels.contains(channel)) {

            channels.add(channel);
        }
    }

    // ====================================
    // Change Status
    // ====================================
    public void changeStatus(Status newStatus) {

        if (status == null) {
            System.out.println(
                    "Parcel has not been booked."
            );
            return;
        }

        if (status == Status.CANCELLED) {
            System.out.println(
                    "Invalid transition: Parcel is cancelled."
            );
            return;
        }

        if (isValidTransition(status, newStatus)) {

            status = newStatus;

            notifyChannels();

        } else {

            System.out.println(
                    "Invalid transition: "
                            + status
                            + " → "
                            + newStatus
                            + " is not allowed."
            );
        }
    }

    // ====================================
    // Check Valid Status Transition
    // ====================================
    private boolean isValidTransition(
            Status current,
            Status next
    ) {

        if (current == Status.BOOKED &&
                next == Status.PICKED_UP) {

            return true;
        }

        if (current == Status.PICKED_UP &&
                next == Status.IN_TRANSIT) {

            return true;
        }

        if (current == Status.IN_TRANSIT &&
                next == Status.OUT_FOR_DELIVERY) {

            return true;
        }

        if (current == Status.OUT_FOR_DELIVERY &&
                next == Status.DELIVERED) {

            return true;
        }

        return false;
    }

    // ====================================
    // Cancel Parcel
    // ====================================
    public void cancel() {

        if (status == Status.BOOKED) {

            status = Status.CANCELLED;

            notifyChannels();

            System.out.println(
                    "Parcel " + parcelId
                            + " cancelled."
            );

        } else {

            System.out.println(
                    "Cancellation failed: "
                            + parcelId
                            + " can be cancelled only while BOOKED."
            );
        }
    }

    // ====================================
    // Notify All Channels
    // ====================================
    private void notifyChannels() {

        for (NotificationChannel channel : channels) {

            channel.notify(
                    parcelId,
                    status
            );
        }
    }

    // ====================================
    // Getters
    // ====================================
    public String getParcelId() {
        return parcelId;
    }

    public Status getStatus() {
        return status;
    }

    public double getWeight() {
        return weight;
    }

    public double getCharge() {
        return shippingType.calculateCharge(weight);
    }

    public Customer getCustomer() {
        return customer;
    }
}

// ========================================
// Parcel Service
// ========================================
class ParcelService {

    public void bookParcel(Parcel parcel) {
        parcel.book();
    }

    public void pickUp(Parcel parcel) {
        parcel.changeStatus(Parcel.Status.PICKED_UP);
    }

    public void moveToInTransit(Parcel parcel) {
        parcel.changeStatus(Parcel.Status.IN_TRANSIT);
    }

    public void moveToOutForDelivery(Parcel parcel) {
        parcel.changeStatus(Parcel.Status.OUT_FOR_DELIVERY);
    }

    public void deliver(Parcel parcel) {
        parcel.changeStatus(Parcel.Status.DELIVERED);
    }

    public void cancel(Parcel parcel) {
        parcel.cancel();
    }
}

// ========================================
// Main Class
// ========================================
public class SwiftShipParcelTracker {

    public static void main(String[] args) {

        // Customer
        Customer customer =
                new Customer("Customer 1");

        // Express parcel
        Parcel parcel =
                new Parcel(
                        "P101",
                        2,
                        new ExpressShipping(),
                        customer
                );

        // Notification channels
        SmsChannel sms =
                new SmsChannel();

        EmailChannel email =
                new EmailChannel();

        // Subscribe to notifications
        parcel.subscribe(sms);
        parcel.subscribe(email);

        // Parcel service
        ParcelService service =
                new ParcelService();

        // =================================
        // 1. Book parcel
        // =================================
        service.bookParcel(parcel);

        // =================================
        // 2. Courier picks up parcel
        // =================================
        service.pickUp(parcel);

        // =================================
        // 3. Customer tries to cancel
        // =================================
        service.cancel(parcel);

        // =================================
        // 4. Move to IN_TRANSIT
        // =================================
        service.moveToInTransit(parcel);

        // =================================
        // 5. Try to directly deliver
        // =================================
        service.deliver(parcel);
    }
}