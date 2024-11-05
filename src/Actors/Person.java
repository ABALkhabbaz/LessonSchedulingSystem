package Actors;

import java.util.ArrayList;
import Offerings.Offering;

public class Person {
    
    public Person() {}

    // Static method to display available offerings
    public static void displayAvailableOfferings(ArrayList<Offering> offerings) {
        System.out.println("Available Offerings:");
        for (Offering offering : offerings) {
            System.out.println("- " + offering.getLesson().getDiscipline());
        }
    }
}
