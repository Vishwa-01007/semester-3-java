public class F1LibraryFineSystem {

    static class BookIssue {
        private String title;
        private String borrowerName;
        private int daysOverdue;

        BookIssue(String title, String borrowerName, int daysOverdue) {
            this.title = title;
            this.borrowerName = borrowerName;
            this.daysOverdue = daysOverdue;
        }

        double fineAmount() {
            if (daysOverdue > 0) {
                return daysOverdue * 5;
            }
            return 0;
        }

        boolean isSeverelyOverdue() {
            return daysOverdue > 14;
        }

        /*
         * totalFineCollected() is static because it calculates the total
         * fine for multiple BookIssue objects. It does not belong to
         * any single book. fineAmount() is not static because it uses
         * the data of one particular BookIssue object.
         */
        static double totalFineCollected(BookIssue[] issues) {
            double total = 0;

            for (BookIssue issue : issues) {
                total += issue.fineAmount();
            }

            return total;
        }

        void printDetails() {
            if (isSeverelyOverdue()) {
                System.out.println(
                        title + " - " + daysOverdue +
                                " days - Severely overdue"
                );
            } else {
                System.out.println(
                        title + " - " + daysOverdue +
                                " days - OK"
                );
            }
        }
    }

    public static void main(String[] args) {

        BookIssue[] issues = {
                new BookIssue("Clean Code", "Aditi", 18),
                new BookIssue("Effective Java", "Rohan", 5),
                new BookIssue("Refactoring", "Arun", 0),
                new BookIssue("DSA Handbook", "Priya", 21),
                new BookIssue("Design Patterns", "Karan", 9)
        };

        for (BookIssue issue : issues) {
            issue.printDetails();
        }

        System.out.println(
                "Total fine collected: Rs " +
                        BookIssue.totalFineCollected(issues)
        );
    }
}