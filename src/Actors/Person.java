package Actors;

import java.util.ArrayList;
import Offerings.Offering;

public class Person {
    private String name;
    private String phone;

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Static method to display available offerings
    public static void displayAvailableOfferings(ArrayList<Offering> offerings) {
        System.out.println("Available Offerings:");
        for (Offering offering : offerings) {
            System.out.println("- " + offering.getLesson().getDiscipline());
        }
    }
}
