public class RunnerEntry extends RaceEntry {

    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }

    @Override
    public void announce() {
        System.out.println(
                "Runner Entry | Bib: " + getBibNumber()
                        + " | Category: " + category
                        + " | Balance: " + getBalanceDue()
        );
    }
}