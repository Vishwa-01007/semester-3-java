import java.util.*;

public class TypingAccuracyChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of words: ");
        int n = sc.nextInt();
        sc.nextLine();

        int correct = 0;

        for (int i = 1; i <= n; i++) {
            System.out.print("Enter expected word: ");
            String expected = sc.nextLine();

            System.out.print("Enter typed word: ");
            String typed = sc.nextLine();

            if (expected.equals(typed)) {
                correct++;
            }
        }

        double accuracy = ((double) correct / n) * 100;

        System.out.println("Correct words: " + correct);
        System.out.println("Accuracy: " + accuracy + "%");

        sc.close();
    }
}