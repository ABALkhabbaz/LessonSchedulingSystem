package MySystem;

import Actors.*;
import Offerings.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MySystem {

    private ArrayList<User> users;
    private ArrayList<Offering> offerings;
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
            System.out.println("1. View available lessons");
            // TODO: Add book a lesson
            numMenuOptions = 1; // Update with actual number of options
        } else if (user instanceof Instructor) {
            System.out.println("1. Select lessons");
            numMenuOptions = 1; // Update with actual number of options
        } else if (user instanceof Admin) {
            // TODO: Display Admin Menu Selection
            System.out.println("1. Add lessons");
            numMenuOptions = 1; // Update with actual number of options
        } else if (user instanceof Person || user == null) {
            // TODO: Display Public Menu Selection
            System.out.println("1. View available lessons");
            numMenuOptions = 1;
        } else {
            System.err.println("Invalid User Type. Exiting Program.");
            System.exit(0);
        }

        System.out.println(++numMenuOptions + ". Exit");

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

        // Adds the admin
        Admin admin = new Admin("admin", "", 0, "admin", "admin");
        users.add(admin);


        displayWelcomeMenu();

        // Login process, passing users and scanner to LoginSystem
        Person user = LoginSystem.promptUserLoginOrCreateAccount(users, scan);

        while(true) {
            int choice = displayMenuOption(user); // Display the menu options based on the user type
            
            if(user instanceof Client){
                if(choice == 1) {
                    Person.displayAvailableOfferings(offerings);
                } 
                if(choice == 2) {

                }
                if(choice == 3){
                    // Quitting
                    break;
                }
            } else if (user instanceof Instructor) {
                if (choice == 1) {
                    // Select lessons
                    ((Instructor) user).selectLesson(offerings, scan);
                }
                
                if (choice == 3) {
                    // Quitting
                    break;
                }

            } else if (user instanceof Admin){
                if (choice == 1) {
                    // Adds Lesson
                    ((Admin) user).addOffering(offerings, scan);
                }

                if(choice == 2) {
                    // Quitting 
                    break;
                }

            } else {
                if (choice == 1) {
                    Person.displayAvailableOfferings(offerings);
                }

                if(choice == 2) {
                    // Quitting
                    break;
                }
            }
            
        }

        // Keep Scanner open until the program is finished.
        this.close(); // Ensures System closes properly
    }

    public void close() {
        System.out.println("Closing Application");
        scan.close();
    }
}
