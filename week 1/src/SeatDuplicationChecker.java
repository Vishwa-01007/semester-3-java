import java.util.*;

public class SeatDuplicationChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        Set<Integer> seats = new HashSet<>();
        boolean duplicate = false;

        System.out.println("Enter seat numbers:");

        for (int i = 0; i < n; i++) {
            int seat = sc.nextInt();

            if (!seats.add(seat)) {
                duplicate = true;
                System.out.println("Duplicate seat found: " + seat);
            }
        }

        if (!duplicate) {
            System.out.println("No duplicate seats found.");
        } else {
            System.out.println("Seat allocation contains duplicates.");
        }

        sc.close();
    }
}