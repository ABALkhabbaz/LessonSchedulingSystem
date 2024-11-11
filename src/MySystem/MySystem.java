package MySystem;

import java.sql.SQLException;
import java.util.Scanner;
import Actors.Admin;
import Actors.Client;
import Actors.Instructor;
import Actors.User;
import Actors.Person;
import DAO.DatabaseHandler;

public class MySystem {
    private LoginSystem loginSystem;
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

        loginSystem = new LoginSystem();
        scanner = new Scanner(System.in);

    }

    // Run the system - Main loop
    public void run() {
        boolean isRunning = true;

        while (isRunning) {

            displayWelcomeMenu();
            User user = loginSystem.promptUserLoginOrCreateAccount(dbHandler, scanner, this);

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
                                // "2. View Lessons"
                                admin.displayAllLessons(dbHandler);
                                break;
                            case 3:
                                // "3. Update Lesson"
                                admin.updateLesson(dbHandler, scanner);
                                break;
                            case 4:
                                // "4. Logout"
                                System.out.println("Logging out...");
                                userSession = false;
                                break;
                            case 5:
                                // "5. Exit Program"
                                close(0);
                                break;
                            default:
                                break;
                        }
                    } else if (user instanceof Instructor) {
                        switch (option) {
                            case 1:
                                // "1. Select Offering"
                                instructor.selectLesson(dbHandler, scanner);
                                // Issue here
                                break;
                            case 2:
                                // "2. View Availabe Offerings"
                                Person.displayAvailableLessons(dbHandler);
                                break;
                            case 3:
                                // "3. Remove yourself from a lesson"
                                instructor.updateLesson(dbHandler, scanner);
                                break;
                            case 4:
                                // "4. Logout"
                                System.out.println("Logging out...");
                                userSession = false;
                                break;
                            case 5:
                                // "5. Exit Program"
                                close(0);
                                break;
                            default:
                                break;
                        }
                    } else if (user instanceof Client) {
                        if(user.getAge() < 18) { // Underage menu
                            switch (option) {
                                case 1:
                                    // "1. Browse Offerings"
                                    Person.displayAvailableLessons(dbHandler);
                                    break;
                                case 2:
                                    // "2. Enroll in Lesson"
                                    client.enrollInLesson(dbHandler, scanner, false);
                                    break;
                                case 3:
                                    // "3. Unenroll From a lesson"
                                    client.unenrollFromLesson(dbHandler, scanner);
                                    break;
                                case 4:
                                    // "4. Logout"
                                    System.out.println("Logging out...");
                                    userSession = false;
                                    break;
                                case 5:
                                    // "5. Exit Program"
                                    close(0);
                                    break;
                                default:
                                    break;
                            }
                        } else { // of age
                            switch (option) {
                                case 1:
                                    // "1. Browse Offerings"
                                    Person.displayAvailableLessons(dbHandler);
                                    break;
                                case 2:
                                    // "2. Enroll in Lesson"
                                    client.enrollInLesson(dbHandler, scanner, false);
                                    break;
                                case 3:
                                    // "3. Unenroll From a lesson"
                                    client.unenrollFromLesson(dbHandler, scanner);
                                    break;
                                case 4:
                                    // "4. Enroll for a minor"
                                    client.enrollForMinor(dbHandler, scanner);
                                    break;
                                case 5:
                                    // "5. Logout"
                                    System.out.println("Logging out...");
                                    userSession = false;
                                    break;
                                case 6:
                                    // "6. Exit Program"
                                    close(0);
                                    break;
                                default:
                                    break;
                            }

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
            System.out.println("3. Update Offering");
            System.out.println("4. Logout");
            System.err.println("5. Exit Program");
            option = getUserChoice(1, 5);
        } else if (user instanceof Instructor) {
            System.out.println("Instructor Options:");
            System.out.println("1. Select Offering");
            System.out.println("2. View Available Offerings");
            System.out.println("3. Remove yourself from a lesson");
            System.out.println("4. Logout");
            System.out.println("5. Exit Program");
            option = getUserChoice(1, 5);
        } else if (user instanceof Client) {
            if(user.getAge() < 18) {
                System.out.println("Client Options:");
                System.out.println("1. Browse Offerings");
                System.out.println("2. Enroll in Lesson");
                System.out.println("3. Unenroll From a lesson");
                System.out.println("4. Logout");
                System.out.println("5. Exit Program");
                option = getUserChoice(1, 5);
            } else {
                System.out.println("Client Options:");
                System.out.println("1. Browse Offerings");
                System.out.println("2. Enroll in Lesson");
                System.out.println("3. Unenroll From a lesson");
                System.out.println("4. Enroll for a minor");
                System.out.println("5. Logout");
                System.out.println("6. Exit Program");
                option = getUserChoice(1, 6);
            }
        }
        return option;
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
