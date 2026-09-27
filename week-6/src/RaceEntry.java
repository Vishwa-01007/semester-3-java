import java.util.Arrays;

public class RaceEntry {

    private String bibNumber;
    private double entryFee;
    private double balanceDue;

    private double[] lateFeeHistory;
    private int lateFeeCount;

    private final int entryCode;
    private static int bibCounter = 0;

    // Constructor
    public RaceEntry(String bibNumber, double entryFee) {

        if (bibNumber == null ||
                bibNumber.trim().isEmpty() ||
                bibNumber.length() < 4) {

            throw new IllegalArgumentException("Invalid bib number");
        }

        if (entryFee <= 0) {
            throw new IllegalArgumentException("Entry fee must be positive");
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.balanceDue = entryFee;

        this.lateFeeHistory = new double[10];
        this.lateFeeCount = 0;

        // Increment only after validation
        bibCounter++;
        this.entryCode = bibCounter;
    }

    // Pay amount
    public void pay(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Payment must be positive"
            );
        }

        balanceDue -= amount;

        if (balanceDue < 0) {
            balanceDue = 0;
        }
    }

    // Overloaded pay method
    public void pay(double amount, String mode) {

        pay(amount);

        System.out.println("Paying via " + mode);
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    public double getEntryFee() {
        return entryFee;
    }

    // Late fee
    protected void applyLateFee(double amount) {

        if (lateFeeCount < lateFeeHistory.length) {
            lateFeeHistory[lateFeeCount] = amount;
            lateFeeCount++;
        }

        balanceDue += amount;
    }

    // Defensive copy
    public double[] getLateFeeHistory() {

        return Arrays.copyOf(
                lateFeeHistory,
                lateFeeCount
        );
    }

    // Announcement
    public void announce() {

        System.out.println(
                "Race Entry | Bib: "
                        + bibNumber
                        + " | Balance: "
                        + balanceDue
        );
    }

    // Problem 1
    public static String registerBatch(
            String[] bibNumbers,
            double entryFee) {

        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {

            try {
                new RaceEntry(bib, entryFee);
                registered++;

            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Registered: "
                + registered
                + " | Rejected: "
                + rejected;
    }

    // Problem 2
    public static String classifyGeneration(
            RaceEntry entry) {

        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }

        if (entry instanceof RunnerEntry) {
            return "Single-inheritance descendant";
        }

        return "Base RaceEntry";
    }

    // Problem 2
    public static double getTotalBalanceDue(
            RaceEntry[] entries) {

        double total = 0;

        for (RaceEntry entry : entries) {

            if (entry != null) {
                total += entry.getBalanceDue();
            }
        }

        return total;
    }

    // Problem 4
    public static String announceAll(
            RaceEntry[] entries) {

        StringBuilder report =
                new StringBuilder();

        for (RaceEntry entry : entries) {

            // Polymorphic call
            entry.announce();

            if (entry instanceof RelayTeamEntry) {

                RelayTeamEntry relay =
                        (RelayTeamEntry) entry;

                report.append(
                        "Relay Team | Bib: "
                                + relay.getBibNumber()
                                + " | Team Size: "
                                + relay.getTeamSize()
                                + " | Balance: "
                                + relay.getBalanceDue()
                                + " [Team size via downcast: "
                                + relay.getTeamSize()
                                + "] | "
                );

            } else if (entry instanceof EliteRunnerEntry) {

                EliteRunnerEntry elite =
                        (EliteRunnerEntry) entry;

                report.append(
                        "Elite Runner | Bib: "
                                + elite.getBibNumber()
                                + " | Category: "
                                + elite.getCategory()
                                + " | Sponsor Bonus: "
                                + elite.getSponsorBonus()
                                + " | Balance: "
                                + elite.getBalanceDue()
                                + " | "
                );

            } else if (entry instanceof RunnerEntry) {

                RunnerEntry runner =
                        (RunnerEntry) entry;

                report.append(
                        "Runner Entry | Bib: "
                                + runner.getBibNumber()
                                + " | Category: "
                                + runner.getCategory()
                                + " | Balance: "
                                + runner.getBalanceDue()
                                + " | "
                );

            } else {

                report.append(
                        "Race Entry | Bib: "
                                + entry.getBibNumber()
                                + " | Balance: "
                                + entry.getBalanceDue()
                                + " | "
                );
            }
        }

        return report.toString();
    }

    // Problem 5
    public static boolean isValidDiscountCode(
            String code) {

        if (code == null ||
                code.length() != 5) {

            return false;
        }

        if (code.charAt(0) != 'M') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(2))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(3))) {
            return false;
        }

        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }

        return true;
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    // Problem 5
    public static String settleNight(
            RaceEntry[] entries) {

        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {

            if (entry == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + relay
                + " relay | "
                + individual
                + " individual";
    }
}