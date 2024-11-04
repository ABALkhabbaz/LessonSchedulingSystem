package Actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Offerings.Offering;

public class Instructor extends User {
    private List<String> availableCities;
    private String specialization;

    public Instructor(String name, String phone, String username, String password, List<String> availableCities, String specialization) {
        super(name, phone, username, password);
        this.availableCities = availableCities;
        this.specialization = specialization;
    }

    public Offering selectLesson(ArrayList<Offering> offerings, Scanner scan) {
        System.out.println("Select an offering:");
        for (int i = 0; i < offerings.size(); i++) {
            System.out.println(i + 1 + ". " + offerings.get(i).getLesson().getDiscipline());
        }
        int choice = scan.nextInt();
        if (choice > 0 && choice <= offerings.size()) {
            return offerings.get(choice - 1);
        } else {
            System.out.println("Invalid choice.");
            return null;
        }
    }

    public List<String> getAvailableCities() {
        return availableCities;
    }

    public void setAvailableCities(List<String> availableCities) {
        this.availableCities = availableCities;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public void displayRole() {
        System.out.println("Instructor: " + getName());
    }
}
