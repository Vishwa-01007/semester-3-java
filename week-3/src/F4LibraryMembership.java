public class F4LibraryMembership {

    /*
     * BROKEN VERSION
     *
     * name, memberId and booksIssued are static.
     *
     * This is wrong because static fields are shared by every
     * LibraryMember object. Therefore, creating the second member
     * overwrites the first member's data.
     */
    static class BrokenLibraryMember {

        static String name;
        static String memberId;
        static int booksIssued;

        BrokenLibraryMember(
                String name,
                String memberId,
                int booksIssued) {

            BrokenLibraryMember.name = name;
            BrokenLibraryMember.memberId = memberId;
            BrokenLibraryMember.booksIssued = booksIssued;
        }

        void printName() {
            System.out.println(name);
        }
    }

    /*
     * FIXED VERSION
     *
     * name, memberId and booksIssued are instance fields because
     * each member needs independent values.
     *
     * libraryName is static because there is one library shared
     * by all members.
     *
     * memberCount is static because it represents the total number
     * of members across the entire library.
     */
    static class LibraryMember {

        private String name;
        private String memberId;
        private int booksIssued;

        private static String libraryName = "Central Library";
        private static int memberCount = 0;

        LibraryMember(String name, int booksIssued) {

            this.name = name;
            this.booksIssued = booksIssued;

            memberCount++;

            this.memberId =
                    String.format("LM-%04d", 1000 + memberCount);
        }

        void printMemberCard() {

            System.out.println(
                    name + " | " + memberId
            );
        }

        static void printTotalMembers() {

            System.out.println(
                    "Total members: " + memberCount
            );
        }
    }

    public static void main(String[] args) {

        System.out.println("Broken version:");

        BrokenLibraryMember member1 =
                new BrokenLibraryMember(
                        "Aditi", "LM-1001", 2);

        BrokenLibraryMember member2 =
                new BrokenLibraryMember(
                        "Rohan", "LM-1002", 3);

        member1.printName();
        member2.printName();

        System.out.println(
                "\nFixed version:"
        );

        LibraryMember fixedMember1 =
                new LibraryMember("Aditi", 2);

        LibraryMember fixedMember2 =
                new LibraryMember("Rohan", 3);

        fixedMember1.printMemberCard();
        fixedMember2.printMemberCard();

        LibraryMember.printTotalMembers();
    }
}