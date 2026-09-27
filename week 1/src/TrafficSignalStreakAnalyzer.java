import java.util.*;

public class TrafficSignalStreakAnalyzer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of signals: ");
        int n = sc.nextInt();

        int longestStreak = 0;
        int currentStreak = 0;

        System.out.println("Enter signal values (1 = Green, 0 = Red):");

        for (int i = 0; i < n; i++) {
            int signal = sc.nextInt();

            if (signal == 1) {
                currentStreak++;

                if (currentStreak > longestStreak) {
                    longestStreak = currentStreak;
                }
            } else {
                currentStreak = 0;
            }
        }

        System.out.println("Longest green signal streak: " + longestStreak);

        sc.close();
    }
}