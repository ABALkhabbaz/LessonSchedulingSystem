package MySystem;

import java.util.ArrayList;
import java.util.Scanner;
import Actors.Admin;
import Actors.Client;
import Actors.Instructor;
import Actors.Person;
import Actors.User;
import Offerings.Offering;

public class MySystem {
    private ArrayList<Offering> offerings = new ArrayList<>();
    private LoginSystem loginSystem = new LoginSystem();
    private ArrayList<User> users = new ArrayList<>(); // Keeps track of registered users

    // Display the welcome menu for the system
    public void displayWelcomeMenu() {
        System.out.println("Welcome to the Lesson Scheduling System");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
    }

    // Display options based on the user type
    public int displayMenuOption(Person user) {
        if (user instanceof Admin) {
            System.out.println("Admin Options:");
            System.out.println("1. Create Offering");
            System.out.println("2. View Offerings");
            System.out.println("3. Logout");
        } else if (user instanceof Instructor) {
            System.out.println("Instructor Options:");
            System.out.println("1. Select Offering");
            System.out.println("2. View Offerings");
            System.out.println("3. Logout");
        } else if (user instanceof Client) {
            System.out.println("Client Options:");
            System.out.println("1. Browse Offerings");
            System.out.println("2. Enroll in Class");
            System.out.println("3. Logout");
        }
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    // Process offerings - handles offering creation and management by Admin
    public void processOfferings(Admin admin, Scanner scan) {
        Offering offering = admin.addLesson(offerings, scan);
        if (offering != null) {
            offerings.add(offering);
            System.out.println("Offering added successfully.");
        } else {
            System.out.println("Failed to add offering.");
        }
    }

    // Manage scheduling - For Instructor to select and manage their lessons
    public void manageScheduling(Instructor instructor, Scanner scan) {
        Offering selectedOffering = instructor.selectLesson(offerings, scan);
        if (selectedOffering != null) {
            System.out.println("Lesson selected: " + selectedOffering.getLesson().getDiscipline());
        } else {
            System.out.println("No available offerings selected.");
        }
    }

    // Display available offerings to the public or any user type
    public void displayAvailableOfferings() {
        if (offerings.isEmpty()) {
            System.out.println("No offerings available at the moment.");
        } else {
            System.out.println("Available Offerings:");
            for (Offering offering : offerings) {
                System.out.println("- " + offering.getLesson().getDiscipline() + " by " + offering.getLesson().getInstructor().getName());
            }
        }
    }

    // Run the system - Main loop
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            displayWelcomeMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Login
                    User user = loginSystem.loginUser(users, scanner);
                    if (user != null) {
                        System.out.println("Login successful. Welcome, " + user.getName() + "!");
                        boolean userSession = true;

                        while (userSession) {
                            int option = displayMenuOption(user);

                            if (user instanceof Admin && option == 1) {
                                processOfferings((Admin) user, scanner);
                            } else if (user instanceof Instructor && option == 1) {
                                manageScheduling((Instructor) user, scanner);
                            } else if (option == 2) {
                                displayAvailableOfferings();
                            } else if (option == 3) {
                                System.out.println("Logging out...");
                                userSession = false;
                            } else {
                                System.out.println("Invalid option. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Login failed. Please try again.");
                    }
                    break;

                case 2: // Register
                    loginSystem.createNewUser(users, scanner);
                    System.out.println("Registration successful.");
                    break;

                case 3: // Exit
                    System.out.println("Exiting the system...");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
        close();
    }

    // Close the system - Cleanup or closing actions
    public void close() {
        System.out.println("Thank you for using the Lesson Scheduling System. Goodbye!");
    }
}
