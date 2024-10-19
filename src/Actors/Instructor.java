package Actors;

import Offerings.Offering;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instructor extends User {
    private ArrayList<String> availableCities;
    private String specialization;

    public Instructor(String name, String phone, int age, String username, String password) {
        super(name, phone, age, username, password);
    }

    // Getter methods
    public List<String> getAvailableCities() {
        return availableCities;
    }

    public String getSpecialization() {
        return specialization;
    }


    public Offering selectLesson(ArrayList<Offering> offerings, Scanner scan) {
        System.out.println("====================================");
        System.out.println("Available Lessons in Your Cities:");

        // Track offerings that match the available cities and have no instructor
        boolean offeringFound = false;
        int count = 0; // For displaying the selection index
        ArrayList<Offering> cityOfferings = new ArrayList<>(); // Store matching offerings

        // Display offerings that match available cities and are unassigned
        for (int i = 0; i < offerings.size(); i++) {
            Offering offering = offerings.get(i);

            // Check if the offering has no assigned instructor and matches the instructor's available cities
            if (offering.getInstructor() == null && availableCities.contains(offering.getLocation().getCity())) {
                System.out.println(count + " - " + offering.toString());
                cityOfferings.add(offering);
                offeringFound = true;
                count++;
            }
        }

        // If no matching offerings are found
        if (!offeringFound) {
            System.out.println("No unassigned offerings available in your cities.");
            System.out.println("====================================");
            return null;
        }

        // Prompt the instructor to select a lesson
        while (true) {
            System.out.print("Enter the index of the lesson you wish to select: ");
            String userInput = scan.nextLine();

            try {
                int index = Integer.parseInt(userInput);

                // Validate the index
                if (index >= 0 && index < cityOfferings.size()) {
                    Offering selectedOffering = cityOfferings.get(index);
                    // Assign the offering to the instructor
                    selectedOffering.setInstructor(this);
                    System.out.println("Lesson selected and assigned: " + selectedOffering);
                    return selectedOffering; // Return the newly assigned offering
                } else {
                    System.err.println("Invalid index. Please enter a valid number.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
            }
        }
    }

    // Setter methods
    public void setAvailableCities(ArrayList<String> availableCities) {
        this.availableCities = availableCities;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
