import java.util.*;

public class UserInputTable
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Define a table to store values (2D array)
        String[][] table = new String[3][3]; // 3x3 table for demonstration

        // Loop to capture input for the table
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.println("Enter value for cell [" + i + "][" + j + "]: ");
                table[i][j] = scanner.nextLine(); // Store value in the array
            }
        }

        // Display the table
        System.out.println("\nStored Table:");
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}


