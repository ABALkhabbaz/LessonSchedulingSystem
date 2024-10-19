import Actors.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MySystem {

    private ArrayList<User> users;
    private ArrayList<Lesson> offerings;
    private Scanner scan = new Scanner(System.in); // Scanner is created once and reused.

    public MySystem() {
        users = new ArrayList<>();
        offerings = new ArrayList<>();
    }

    private void displayWelcomeMenu() {
        System.out.println("====================================");
        System.out.println("Welcome to the Lesson Scheduling System!");
        System.out.println("====================================");
    }

    private int displayMenuOption(Person user) {
        System.out.println("====================================");
        System.out.println("Menu Options: ");

        int numMenuOptions = 0;
        if (user instanceof Client) {
            // TODO: Display Client Menu Selection
            System.out.println("1. View available lessons");
            System.out.println("2. Book a lesson");
            numMenuOptions = 2; // Update with actual number of options
        } else if (user instanceof Instructor) {
            // TODO: Display Instructor Menu Selection
            System.out.println("1. View assigned lessons");
            System.out.println("2. Mark attendance");
            numMenuOptions = 2; // Update with actual number of options
        } else if (user instanceof Admin) {
            // TODO: Display Admin Menu Selection
            System.out.println("1. Manage lessons");
            System.out.println("2. View all bookings");
            System.out.println("3. Manage users");
            numMenuOptions = 3; // Update with actual number of options
        } else if (user instanceof Person) {
            // TODO: Display Public Menu Selection
            System.out.println("1. View available lessons");
            numMenuOptions = 1;
        } else {
            System.err.println("Invalid User Type. Exiting Program.");
            System.exit(0);
        }

        System.out.println((numMenuOptions + 1) + ". Exit");

        System.out.print("Enter your choice: ");
        int choice = -1;

        while (true) {
            String userInput = scan.nextLine();

            try {
                choice = Integer.parseInt(userInput);

                if (choice < 1 || choice > numMenuOptions) {
                    System.err.println("Invalid input. Please enter a number between 1 and " + numMenuOptions + ".");
                    System.out.print("Enter your choice: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
                System.out.print("Enter your choice: ");
            }
        }

        System.out.println("====================================");
        System.out.println("\n\n\n\n\n");

        return choice;
    }

    public void run() {
        displayWelcomeMenu();

        // Login process, passing users and scanner to LoginSystem
        Person user = LoginSystem.promptUserLoginOrCreateAccount(users, scan);

        // Ask user to login or create an account
        if (user != null) {
            displayMenuOption(user); // Display the menu options based on the user type
        } else {
            System.out.println("No user logged in.");
        }

        // Keep Scanner open until the program is finished.
        this.close(); // Ensures System closes properly
    }

    public void close() {
        scan.close();
    }
}
