import java.util.ArrayList;
import java.util.Scanner;

public class MySystem {

    private ArrayList<User> users;
    private ArrayList<Lesson> offerings;

    private void displayWelcomeMenu(){
        System.out.println("====================================");
        System.out.println("Welcome to the Lesson Scheduling System!");
        System.out.println("====================================");
    }

    private void displayUserLoginMenu(Person user) {
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("3. Skip Login"); // For public access

        System.out.print("Enter your choice: ");
        int choice = -1;

        Scanner scan = new Scanner(System.in);

        while(true){
            String userInput = scan.nextLine();

            try {
                choice = Integer.parseInt(userInput);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
                System.out.print("Enter your choice: ");
            }
        }
        scan.close();

        scan = new Scanner(System.in);
        switch(choice){
            case 1:
                // Login
                
                user.login();
                break;
            case 2:
                // Create an account
                System.out.println("Creating an account...");
                System.out.print("Name: ");
                String name = scan.nextLine();
                System.out.print("Phone: ");
                String phone = scan.nextLine();


    }

    private int displayMenuOption(Person user){

        System.out.println("Menu Options: ");

        int numMenuOptions = 0;
        if(user instanceof Client){
            // TODO: Display Client Menu Selection

            numMenuOptions = 0; // Make sure to update this value
        } else if(user instanceof Instructor){
            // TODO: Display Instructor Menu Selection

            numMenuOptions = 0; // Make sure to update this value
        } else if(user instanceof Admin){
            // TODO: Display Admin Menu Selection

            numMenuOptions = 0; // Make sure to update this value
        } else {
            System.err.println("Invalid User Type. Exiting Program.");
            System.exit(0);
        }
        
        System.out.println(numMenuOptions + ". Exit");
        
        System.out.print("Enter your choice: ");
        int choice = -1;

        Scanner scan = new Scanner(System.in);

        while(true){
            String userInput = scan.nextLine();

            try {
                choice = Integer.parseInt(userInput);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
                System.out.print("Enter your choice: ");
            }
        }

        scan.close();

        return choice;
    }

    public void run() {

        displayWelcomeMenu();

        // Create a new instance of Person
        Person user = new Person();

        // Ask user to login or create an account
        displayUserLoginMenu(user);

        // Display the menu options
        displayMenuOption(user);



    }
}
