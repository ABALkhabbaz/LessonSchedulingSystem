package MySystem;

import Actors.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
                return null; // Returning null to indicate no login
            default:
                return null;
        }
    }

    public static User loginUser(ArrayList<User> users, Scanner scan) {
        while (true) { // Infinite loop for retries
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
                return foundUser; // Successfully authenticated
            } else {
                System.err.println("Invalid username or password.");

                // Ask if the user wants to try again or exit
                System.out.print("Would you like to try again? (y/n): ");
                String retry = scan.nextLine().trim().toLowerCase();
                if (!retry.equals("y")) {
                    // If the user doesn't want to retry, return new Person
                    System.out.println("Exiting login...");
                    return null; // Login failed, returning non-logged in client
                }
            }
        }
    }

    public static Person createNewUser(ArrayList<User> users, Scanner scan) {
        while (true) { // Loop for retries
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
                    break; // Username is valid, break out of loop
                }
            }

            // Prompt for the remaining user details
            System.out.print("Password: ");
            String password = scan.nextLine();
            System.out.print("Name: ");
            String name = scan.nextLine();
            System.out.print("Phone: ");
            String phone = scan.nextLine();

            // Entering valid birthDate
            LocalDate birthDate = null;
            boolean validDate = false;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            while (!validDate) {
                System.out.print("Enter your birthdate (YYYY-MM-DD): ");
                String dateInput = scan.nextLine();
                try {
                    birthDate = LocalDate.parse(dateInput, formatter);
                    if (birthDate.isAfter(LocalDate.now())) {
                        System.out.println("Birthdate cannot be in the future. Please try again.");
                    } else {
                        validDate = true; // Date is valid
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                }
            }

            // Create new user and add to system
            long userId = 0; // For now all userId = 0, once db is connected, well let the db handle the user id
            User newUser = new User(userId, name, phone, birthDate, username, password);
            users.add(newUser); // Add the new user to the system

            System.out.println("Account created successfully!");
            return newUser; // Return the newly created user
        }
    }
}
