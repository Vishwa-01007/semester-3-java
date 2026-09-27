public class RelayTeamEntry extends RaceEntry {

    private int teamSize;

    public RelayTeamEntry(String bibNumber,
                          double entryFee,
                          int teamSize) {

        super(bibNumber, entryFee);

        if (teamSize <= 0) {
            throw new IllegalArgumentException(
                    "Team size must be positive"
            );
        }

        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public void announce() {

        System.out.println(
                "Relay Team | Bib: " +
                        getBibNumber() +
                        " | Team Size: " +
                        teamSize +
                        " | Balance: " +
                        getBalanceDue()
        );
    }
}