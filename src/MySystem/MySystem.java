package MySystem;

import java.util.ArrayList;
import java.util.Scanner;
import Actors.Admin;
import Actors.Client;
import Actors.Instructor;
import Actors.Person;
import Actors.User;
import Offerings.Lesson;

public class MySystem {
    private ArrayList<Lesson> lessons = new ArrayList<Lesson>();
    private LoginSystem loginSystem = new LoginSystem();
    private ArrayList<User> users = new ArrayList<User>(); // Keeps track of registered users
    private Scanner scanner = new Scanner(System.in);

    // Run the system - Main loop
    public void run() {
        boolean isRunning = true;

        while (isRunning) {

            displayWelcomeMenu();
            User user = loginSystem.promptUserLoginOrCreateAccount(users, scanner);

            if (user != null) {
                System.out.println("Login successful. Welcome, " + user.getName() + "!");
                boolean userSession = true;

                while (userSession) {
                    int option = displayMenuOption(user);

                    if (user instanceof Admin && option == 1) {
                        processOfferings((Admin) user);
                    } else if (user instanceof Instructor && option == 1) {
                        manageScheduling((Instructor) user);
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
                System.out.println("Client Option");
                System.out.println("1. Display Available Lessons");
                System.out.println("2. Main menu");
                System.out.println("3. Exit program");
                int option = getUserChoice(1,3);

                switch (option) {
                    case 1:
                        displayAvailableOfferings();
                        break;
                    case 2:
                        // Go back to main menu
                        break;
                    case 3:
                        // Exit
                        isRunning = false;
                        break;
                }
            }
        }

        close();
    }

    // Close the system - Cleanup or closing actions
    public void close() {

        scanner.close(); // Closes scanner
        System.out.println("Thank you for using the Lesson Scheduling System. Goodbye!");
    }

    // Display the welcome menu for the system
    public void displayWelcomeMenu() {
        System.out.println("Welcome to the Lesson Scheduling System");
    }

    // Display options based on the user type
    public int displayMenuOption(User user) {
        int option = -1;
        if (user instanceof Admin) {
            System.out.println("Admin Options:");
            System.out.println("1. Create Offering");
            System.out.println("2. View Offerings");
            System.out.println("3. Logout");
            option = getUserChoice(1, 3);
        } else if (user instanceof Instructor) {
            System.out.println("Instructor Options:");
            System.out.println("1. Select Offering");
            System.out.println("2. View Offerings");
            System.out.println("3. Logout");
            option = getUserChoice(1, 3);
        } else if (user instanceof Client) {
            System.out.println("Client Options:");
            System.out.println("1. Browse Offerings");
            System.out.println("2. Enroll in Class");
            System.out.println("3. Logout");
            option = getUserChoice(1, 3);
        }
        return option;
    }

    // Process offerings - handles offering creation and management by Admin
    public void processOfferings(Admin admin) {
        Lesson lesson = admin.addLesson(lessons, scanner);
        if (lesson != null) {
            lessons.add(lesson);
            System.out.println("Offering added successfully.");
        } else {
            System.out.println("Failed to add offering.");
        }
    }

    // Manage scheduling - For Instructor to select and manage their lessons
    public void manageScheduling(Instructor instructor) {
        Lesson selectedLesson = instructor.selectLesson(lessons, scanner);
        if (selectedLesson != null) {
            System.out.println("Lesson selected: " + selectedLesson.getDiscipline());
        } else {
            System.out.println("No available offerings selected.");
        }
    }

    // Display available offerings to the public or any user type
    public void displayAvailableOfferings() {
        if (lessons.isEmpty()) {
            System.out.println("No offerings available at the moment.");
        } else {
            System.out.println("Available Offerings:");
            for (Lesson lesson : lessons) {
                System.out.println("- " + lesson.getDiscipline() + " by "
                        + lesson.getInstructor().getName());
            }
        }
    }

    public int getUserChoice(int min, int max) {
        int choice = -1;

        // Input validation for user choice
        while (true) {
            System.out.print("Enter your choice: ");

            String userInput = scanner.nextLine().trim(); // Use the instance variable `scanner`

            try {
                choice = Integer.parseInt(userInput); // Attempt to parse choice

                // Validate if choice is within acceptable range
                if (choice >= min && choice <= max) {
                    break; // Valid choice, exit the loop
                } else {
                    System.err.println("Invalid choice. Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number."); // Handle non-integer input
            }
        }
        return choice;
    }

}
