package MySystem;

import Actors.*;
import DAO.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginSystem {

    // Pass in users and scanner from MySystem
    public User promptUserLoginOrCreateAccount(DatabaseHandler dbHandler, Scanner scan, MySystem mySystem) {
        System.out.println("1. Login");
        System.out.println("2. Create a Client Account");
        System.out.println("3. Skip Login"); // For public access
        System.out.println("4. Create a Instructor Account");
        System.out.println("5. Exit Program");

        int choice = new MySystem().getUserChoice(1, 5);

        switch (choice) {
            case 1:
                // Login process
                // return loginUser(users, scan); // old not connected to db
                return loginUser(dbHandler, scan);
            case 2:
                // Create a Client account process
                // return createNewClient(users, scan);
                return createNewClientAccount(dbHandler, scan);
            case 3:
                // Skip login, return a generic user --> Person
                System.out.println("Skipping login...");
                return null;
            case 4:
                // Creating new Instrutor Account
                return createNewInstructorAccount(dbHandler, scan);
            case 5:
                mySystem.close(0); // Exit program
                break;

        }

        return null;
    }

    public static User loginUser(DatabaseHandler dbHandler, Scanner scan) {
        try {
            User foundUser = null;
            while (foundUser == null) { // Infinite loop for retries
                System.out.println("Logging in...");
                System.out.print("Username: ");
                String username = scan.nextLine();
                System.out.print("Password: ");
                String password = scan.nextLine();

                foundUser = dbHandler.getUser(username, password);

                if (foundUser != null)
                    return foundUser; // Successful Login

                // Ask if the user wants to try again or exit
                System.out.print("Would you like to try again? (y/n): ");
                String retry = scan.nextLine().trim().toLowerCase();
                if (!retry.equals("y")) {
                    System.out.println("Exiting login...");
                    return null; // Login failed, returning non-logged-in client
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during login: " + e.getMessage());
            return null;
        }

        return null;
    }

    public static Client createNewClientAccount(DatabaseHandler dbHandler, Scanner scan) {
        while (true) { // Loop for retries
            System.out.println("Creating a Client account...");

            // Username input and validation loop
            String username;
            while (true) {
                System.out.print("Username: ");
                username = scan.nextLine();

                // Check if username already exists in the database
                String checkUsernameSQL = "SELECT 1 FROM Users WHERE username = ?";
                try (ResultSet rs = dbHandler.executeQuery(checkUsernameSQL, username)) {
                    if (rs.next()) {
                        System.err.println("This username already exists. Try again.");
                    } else {
                        break; // Username is available
                    }
                } catch (SQLException e) {
                    System.err.println("Database error: " + e.getMessage());
                    return null;
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

            return dbHandler.insertClient(name, phone, birthDate, username, password);
        }
    }

    public static Instructor createNewInstructorAccount(DatabaseHandler dbHandler, Scanner scan) {

        while (true) { // Loop for retries
            System.out.println("Creating a Instructor account...");

            // Username input and validation loop
            String username;
            while (true) {
                System.out.print("Username: ");
                username = scan.nextLine();

                // Check if username already exists in the database
                String checkUsernameSQL = "SELECT 1 FROM Users WHERE username = ?";
                try (ResultSet rs = dbHandler.executeQuery(checkUsernameSQL, username)) {
                    if (rs.next()) {
                        System.err.println("This username already exists. Try again.");
                    } else {
                        break; // Username is available
                    }
                } catch (SQLException e) {
                    System.err.println("Database error: " + e.getMessage());
                    return null;
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

            // Prompt for specialization
            System.out.print("Specialization: ");
            String specialization = scan.nextLine();

            // Prompt for available cities
            System.out.print("Enter the cities you are available to teach in (comma-separated): ");
            String[] cities = scan.nextLine().split(",");
            ArrayList<String> availableCities = new ArrayList<>();
            for (String city : cities) {
                availableCities.add(city.trim());
            }


            return dbHandler.insertInstructor(name, phone, birthDate, username, password, specialization, availableCities);
        }

    }
}
