public class CanteenRankingEngine {

    static class Canteen {

        private String canteenCode;
        private String canteenName;
        private int trustScore;

        public Canteen(
                String canteenCode,
                String canteenName,
                int trustScore) {

            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }

        public Canteen(
                String canteenCode,
                String canteenName) {

            this(canteenCode, canteenName, 3);
        }

        public int compareTo(Canteen other) {

            // Higher trust score comes first
            if (this.trustScore != other.trustScore) {
                return Integer.compare(
                        other.trustScore,
                        this.trustScore);
            }

            // Tie-break: code, ignoring case
            int codeResult =
                    this.canteenCode.compareToIgnoreCase(
                            other.canteenCode);

            if (codeResult != 0) {
                return codeResult;
            }

            // Final tie-break: shorter name first
            return Integer.compare(
                    this.canteenName.length(),
                    other.canteenName.length());
        }

        public String getCanteenCode() {
            return canteenCode;
        }
    }

    static Canteen[] rankCanteens(Canteen[] canteens) {

        // Manual O(n²) selection sort
        for (int i = 0; i < canteens.length - 1; i++) {

            int bestIndex = i;

            for (int j = i + 1;
                 j < canteens.length;
                 j++) {

                if (canteens[j].compareTo(
                        canteens[bestIndex]) < 0) {

                    bestIndex = j;
                }
            }

            Canteen temp = canteens[i];
            canteens[i] = canteens[bestIndex];
            canteens[bestIndex] = temp;
        }

        return canteens;
    }

    public static void main(String[] args) {

        Canteen[] canteens = {
                new Canteen(
                        "HB3-C",
                        "Spice Junction",
                        3),

                new Canteen(
                        "hb1-c",
                        "Grand Mess",
                        5),

                new Canteen(
                        "HB2-C",
                        "Southern Treats")
        };

        Canteen[] ranked =
                rankCanteens(canteens);

        System.out.println("Ranked canteens:");

        for (Canteen canteen : ranked) {
            System.out.println(
                    canteen.getCanteenCode());
        }
    }
}