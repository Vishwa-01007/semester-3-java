public class DeliverySlotBooking {

    static class DeliverySlot {

        private String orderId;
        private String timeSlot;

        public DeliverySlot(String orderId, String timeSlot) {
            this.orderId = orderId;
            this.timeSlot = timeSlot;
        }

        public DeliverySlot(String orderId) {
            this(orderId, "ASAP");
        }

        public boolean isPeakHour() {

            return timeSlot.equals("12:00-13:00")
                    || timeSlot.equals("13:00-14:00")
                    || timeSlot.equals("19:00-20:00")
                    || timeSlot.equals("20:00-21:00");
        }

        public String getTimeSlot() {
            return timeSlot;
        }
    }

    public static void main(String[] args) {

        DeliverySlot slot1 =
                new DeliverySlot(
                        "ORD101",
                        "13:00-14:00");

        DeliverySlot slot2 =
                new DeliverySlot("ORD102");

        System.out.println(
                "ORD101 peak hour: " +
                        slot1.isPeakHour());

        System.out.println(
                "ORD102 time slot: " +
                        slot2.getTimeSlot());

        System.out.println(
                "ORD102 peak hour: " +
                        slot2.isPeakHour());
    }
}