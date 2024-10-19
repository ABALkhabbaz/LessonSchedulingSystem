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
            // TODO: Display Client Menu Selection
            System.out.println("1. View available lessons");
            System.out.println("2. Book a lesson");
            numMenuOptions = 2; // Update with actual number of options
        } else if (user instanceof Instructor) {
            // TODO: Display Instructor Menu Selection
            System.out.println("1. Select lessons");
            System.out.println("2. Mark attendance");
            numMenuOptions = 2; // Update with actual number of options
        } else if (user instanceof Admin) {
            // TODO: Display Admin Menu Selection
            System.out.println("1. Add lessons");
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

    // Clients and non clients function to view available Offerings
    private void displayAvailableOfferings() {
        // Prints offerings with instuctors assigned
        System.out.println("====================================");
        System.out.println("Available Offerings:");
    
        // Check if the list is empty
        if (offerings.isEmpty()) {
            System.out.println("No offerings available at the moment.");
        } else {
            // Iterate through the offerings list and display each one
            for (int i = 0; i < offerings.size(); i++) {
                Offering offering = offerings.get(i);

                // skips if instructor is null
                if(offering.getInstructor() == null) continue;

                System.out.println(i + ". " + offering.toString()); // Assuming Offering class has a toString() method
            }
        }
    
        System.out.println("====================================");
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
                    user.displayAvailableOfferings(offerings);
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
                    displayAvailableOfferings();
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
