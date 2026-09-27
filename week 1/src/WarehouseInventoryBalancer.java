import java.util.*;

public class WarehouseInventoryBalancer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of products: ");
        int n = sc.nextInt();

        int[] inventory = new int[n];

        System.out.println("Enter inventory quantities:");

        int total = 0;

        for (int i = 0; i < n; i++) {
            inventory[i] = sc.nextInt();
            total += inventory[i];
        }

        double average = (double) total / n;

        System.out.println("Total inventory: " + total);
        System.out.println("Average inventory: " + average);

        System.out.println("Products below average:");

        for (int i = 0; i < n; i++) {
            if (inventory[i] < average) {
                System.out.println(
                        "Product " + (i + 1) + ": " + inventory[i]
                );
            }
        }

        sc.close();
    }
}