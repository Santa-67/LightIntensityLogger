import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LightIntensityLogger {

    private static final String FILE_NAME = "light_data.txt";
    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("=== Light Intensity Logger ===");

        do {
            System.out.println("\n1. Generate and Save Reading");
            System.out.println("2. View All Readings");
            System.out.println("3. Search Readings by Date");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> generateAndSaveReading();
                case 2 -> viewReadings();
                case 3 -> {
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    searchByDate(date);
                }
                case 4 -> System.out.println("Exiting. Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 4);

        scanner.close();
    }

    private static void generateAndSaveReading() {
        int ldrValue = random.nextInt(1024);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(ldrValue + "," + timestamp);
            writer.newLine();
            System.out.println("Reading saved: " + ldrValue + " at " + timestamp);
        } catch (IOException e) {
            System.err.println("Error writing to file.");
        }
    }

    private static void viewReadings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n--- Light Readings ---");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("LDR Value: " + parts[0] + " | Timestamp: " + parts[1]);
            }
        } catch (IOException e) {
            System.err.println("No readings found or error reading file.");
        }
    }

    private static void searchByDate(String date) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n--- Search Results for " + date + " ---");
            while ((line = reader.readLine()) != null) {
                if (line.contains(date)) {
                    String[] parts = line.split(",");
                    System.out.println("LDR Value: " + parts[0] + " | Timestamp: " + parts[1]);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No readings found for this date.");
            }
        } catch (IOException e) {
            System.err.println("Error reading file.");
        }
    }
}
