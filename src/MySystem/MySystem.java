package MySystem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import Actors.Admin;
import Actors.Client;
import Actors.Instructor;
import Actors.User;
import Actors.Person;
import DAO.DatabaseHandler;
import Offerings.Lesson;

public class MySystem {
    private ArrayList<Lesson> lessons;
    private LoginSystem loginSystem;
    private ArrayList<User> users;
    private Scanner scanner;
    private DatabaseHandler dbHandler;

    public MySystem() {

        dbHandler = new DatabaseHandler();
        try {
            dbHandler.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            close(-1);
        }

        lessons = new ArrayList<Lesson>();
        loginSystem = new LoginSystem();
        users = new ArrayList<User>();
        scanner = new Scanner(System.in);

    }

    // Run the system - Main loop
    public void run() {
        boolean isRunning = true;

        while (isRunning) {

            displayWelcomeMenu();
            User user = loginSystem.promptUserLoginOrCreateAccount(users, scanner, dbHandler);

            if (user != null) {
                // User is logged in
                System.out.println("Login successful. Welcome, " + user.getName() + "!"); // We can choose between name
                                                                                          // and username
                boolean userSession = true;
                
                Admin admin = null;
                Instructor instructor = null;
                Client client = null;

                if (user instanceof Admin)
                    admin = (Admin) user;
                else if (user instanceof Instructor)
                    instructor = (Instructor) user;
                else if (user instanceof Client)
                    client = (Client) user;
                else {
                    System.out.println("Invalid user type. Please try again.");
                    close(-1);
                }

                while (userSession) {
                    int option = displayMenuOption(user);

                    if (user instanceof Admin) {
                        switch (option) {
                            case 1:
                                // "1. Create Offering"
                                admin.addLesson(dbHandler, scanner);
                                break;
                            case 2:
                                // "2. View Offerings"
                                admin.displayAllLessons(dbHandler);
                                break;
                            case 3:
                                // "3. Logout"
                                System.out.println("Logging out...");
                                userSession = false;
                                break;
                            case 4:
                                // "4. Exit Program"
                                close(0);
                                break;
                            default:
                                break;
                        }
                    } else if (user instanceof Instructor) {
                        switch (option) {
                            case 1:
                                // "1. Select Offering"
                                manageScheduling((Instructor) user);
                                break;
                            case 2:
                                // "2. View Offerings"
                                Person.displayAvailableLessons(dbHandler);
                                break;
                            case 3:
                                // "3. Logout"
                                System.out.println("Logging out...");
                                userSession = false;
                                break;
                            case 4:
                                // "4. Exit Program"
                                close(0);
                                break;
                            default:
                                break;
                        }
                    } else if (user instanceof Client) {
                        switch (option) {
                            case 1:
                                // "1. Browse Offerings"
                                Person.displayAvailableLessons(dbHandler);
                                break;
                            case 2:
                                // "2. Enroll in Lesson"

                                break;
                            case 3:
                                // "3. Logout"
                                System.out.println("Logging out...");
                                userSession = false;
                                break;
                            case 4:
                                // "4. Exit Program"
                                close(0);
                                break;
                            default:
                                break;
                        }
                    } else {
                        System.out.println("Invalid option. Please try again.");
                        close(-1);
                    }
                }
            } else {
                // Public access -- No user logged in
                System.out.println("Client Option");
                System.out.println("1. Display Available Lessons");
                System.out.println("2. Main menu");
                System.out.println("3. Exit program");
                int option = getUserChoice(1, 3);

                switch (option) {
                    case 1:
                        Person.displayAvailableLessons(dbHandler);
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
    }

    // Close the system - Cleanup or closing actions
    public void close(int exitCode) {
        scanner.close(); // Closes scanner
        dbHandler.disconnect(); // Disconnects db
        System.out.println("Thank you for using the Lesson Scheduling System. Goodbye!");
        System.exit(exitCode);
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
            System.err.println("4. Exit Program");
            option = getUserChoice(1, 4);
        } else if (user instanceof Instructor) {
            System.out.println("Instructor Options:");
            System.out.println("1. Select Offering");
            System.out.println("2. View Offerings");
            System.out.println("3. Logout");
            System.out.println("4. Exit Program");
            option = getUserChoice(1, 4);
        } else if (user instanceof Client) {
            System.out.println("Client Options:");
            System.out.println("1. Browse Offerings");
            System.out.println("2. Enroll in Lesson");
            System.out.println("3. Logout");
            System.out.println("4. Exit Program");
            option = getUserChoice(1, 4);
        }
        return option;
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
