package System;
import Actors.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginSystem {

    // Pass in users and scanner from MySystem
    public static Person promptUserLoginOrCreateAccount(ArrayList<User> users, Scanner scan) {
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("3. Skip Login"); // For public access
    
        int choice = -1;
    
        // Input validation for user choice
        while (true) {
            System.out.print("Enter your choice: ");
            String userInput = scan.nextLine();
    
            try {
                choice = Integer.parseInt(userInput);
    
                if (choice < 1 || choice > 3) {
                    System.err.println("Invalid input. Please enter a number between 1 and 3.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
            }
        }
    
        switch (choice) {
            case 1:
                // Login process
                return loginUser(users, scan);
            case 2:
                // Create account process
                return createNewUser(users, scan);
            case 3:
                // Skip login, return a generic user
                System.out.println("Skipping login...");
                return null;  // Returning null to indicate no login
            default:
                return null;
        }
    }
    
    private static Person loginUser(ArrayList<User> users, Scanner scan) {
        while (true) {  // Infinite loop for retries
            System.out.println("Logging in...");
            System.out.print("Username: ");
            String username = scan.nextLine();
            System.out.print("Password: ");
            String password = scan.nextLine();
    
            // Find the user by username
            User foundUser = null;
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    foundUser = user;
                    break;
                }
            }
    
            // Check if the user is found and if the password matches
            if (foundUser != null && foundUser.getPassword().equals(password)) {
                System.out.println("Login successful. Welcome " + foundUser.getUsername() + "!");
                return foundUser;  // Successfully authenticated
            } else {
                System.err.println("Invalid username or password.");
    
                // Ask if the user wants to try again or exit
                System.out.print("Would you like to try again? (y/n): ");
                String retry = scan.nextLine().trim().toLowerCase();
                if (!retry.equals("y")) {
                    // If the user doesn't want to retry, return new Person
                    System.out.println("Exiting login...");
                    return new Person();  // Login failed, returning non-logged in client
                }
            }
        }
    }

    private static Person createNewUser(ArrayList<User> users, Scanner scan) {
        while (true) {  // Loop for retries
            System.out.println("Creating an account...");
    
            // Username input and validation loop
            String username;
            while (true) {
                System.out.print("Username: ");
                username = scan.nextLine();
    
                // Check if username already exists
                boolean usernameExists = false;
                for (User user : users) {
                    if (user.getUsername().equals(username)) {
                        System.err.println("This username already exists. Try again.");
                        usernameExists = true;
                        break;
                    }
                }
    
                if (!usernameExists) {
                    break;  // Username is valid, break out of loop
                }
            }
    
            // Prompt for the remaining user details
            System.out.print("Password: ");
            String password = scan.nextLine();
            System.out.print("Name: ");
            String name = scan.nextLine();
            System.out.print("Phone: ");
            String phone = scan.nextLine();
    
            // Age input and validation loop
            int age = 0;
            while (true) {
                System.out.print("Age: ");
                try {
                    age = Integer.parseInt(scan.nextLine());
                    if (age < 0) {
                        System.err.println("Age cannot be negative. Try again.");
                    } else {
                        break;  // Valid age, exit loop
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid input for age. Please enter a valid number.");
                }
            }
    
            // Create new user and add to system
            User newUser = new User(name, phone, age, username, password);
            users.add(newUser);  // Add the new user to the system
    
            System.out.println("Account created successfully!");
            return newUser;  // Return the newly created user
        }
    }    
}
