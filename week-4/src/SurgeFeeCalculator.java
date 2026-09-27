public final class SurgeFeeCalculator {

    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {

        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException(
                    "Minimum surge percent cannot be negative");
        }

        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(
            double orderValue,
            int delayMinutes) {

        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException(
                    "Order value and delay cannot be negative");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        double tieredFee = 0.0;

        // Minutes 1-5: 0.5%
        int firstTier =
                Math.min(delayMinutes, 5);

        tieredFee +=
                orderValue * 0.005 * firstTier;

        // Minutes 6-15: 1%
        if (delayMinutes > 5) {

            int secondTier =
                    Math.min(delayMinutes, 15) - 5;

            tieredFee +=
                    orderValue * 0.01 * secondTier;
        }

        // Minute 16 onwards: 2%
        if (delayMinutes > 15) {

            int thirdTier =
                    delayMinutes - 15;

            tieredFee +=
                    orderValue * 0.02 * thirdTier;
        }

        // Minimum floor applies only when delayed
        double minimumFee =
                orderValue *
                        (minimumSurgePercent / 100.0);

        return Math.max(tieredFee, minimumFee);
    }

    public static void main(String[] args) {

        SurgeFeeCalculator calculator =
                new SurgeFeeCalculator(1.0);

        System.out.println(
                "Delay 0: Rs " +
                        calculator.calculateSurgeFee(500, 0));

        System.out.println(
                "Delay 1: Rs " +
                        calculator.calculateSurgeFee(500, 1));

        System.out.println(
                "Delay 16: Rs " +
                        calculator.calculateSurgeFee(500, 16));
    }
}