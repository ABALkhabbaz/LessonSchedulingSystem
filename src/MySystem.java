import java.util.ArrayList;
import java.util.Scanner;

public class MySystem {

    private ArrayList<User> users;
    private ArrayList<Lesson> offerings;

    private Scanner scan = new Scanner(System.in); // Scanner is created once and reused.

    private void displayWelcomeMenu() {
        System.out.println("====================================");
        System.out.println("Welcome to the Lesson Scheduling System!");
        System.out.println("====================================");
    }

    private Person displayUserLoginMenu() {
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("3. Skip Login"); // For public access

        System.out.print("Enter your choice: ");
        int choice = -1;

        while (true) {
            String userInput = scan.nextLine();

            try {
                choice = Integer.parseInt(userInput);

                if (choice < 1 || choice > 3) {
                    System.err.println("Invalid input. Please enter a number between 1 and 3.");
                    System.out.print("Enter your choice: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
                System.out.print("Enter your choice: ");
            }
        }

        switch (choice) {
            case 1:
                // TODO: Login
                System.out.println("Logging in...");
                System.out.print("Username: ");
                String username = scan.nextLine();
                System.out.print("Password: ");
                String password = scan.nextLine();
                // Must implement logic to check if the username and password are valid
                break;
            case 2:
                // TODO: Create an account
                System.out.println("Creating an account...");
                System.out.print("Username: ");
                username = scan.nextLine();
                System.out.print("Password: ");
                password = scan.nextLine();
                System.out.print("Name: ");
                String name = scan.nextLine();
                System.out.print("Phone: ");
                String phone = scan.nextLine();
                // Must implement logic to create an account

                User newUser = new User(username, password, name, phone);
                users.add()

                break;
            case 3:
                System.out.println("Skipping login...");
                return new Person();
        }

        System.out.println("====================================");
        System.out.println("\n\n\n\n\n");
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

        System.out.println((numMenuOptions+1) + ". Exit");

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

        // Loggin process
        Person user = displayUserLoginMenu();  // TODO: Adjust this based on login logic

        // Ask user to login or create an account
        

        // Display the menu options based on the user type
        displayMenuOption(user);

        // Keep Scanner open until the program is finished.

        this.close(); // Ensures System closes properly
    }

    public void close() {
        scan.close();
    }
}
