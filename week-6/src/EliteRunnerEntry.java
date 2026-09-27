public class EliteRunnerEntry extends RunnerEntry {

    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber,
                            double entryFee,
                            String category,
                            double sponsorBonus) {

        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    public double getSponsorBonus() {
        return sponsorBonus;
    }

    @Override
    public void announce() {

        System.out.println(
                "Elite Runner | Bib: " +
                        getBibNumber() +
                        " | Category: " +
                        getCategory() +
                        " | Sponsor Bonus: " +
                        sponsorBonus +
                        " | Balance: " +
                        getBalanceDue()
        );
    }
}