package Actors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Offerings.Lesson;

public class Instructor extends User {
    private List<String> availableCities;
    private String specialization;

    public Instructor(long userId, String name, String phone, LocalDate birthDate, String username, String password, String specialization, ArrayList<String> availableCities) {
        super(userId, name, phone, birthDate, username, password);
        this.availableCities = availableCities;
        this.specialization = specialization;
    }

    public Lesson selectLesson(ArrayList<Lesson> lessons, Scanner scan) {
        System.out.println("Select an offering:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println(i + 1 + ". " + lessons.get(i).getDiscipline());
        }
        int choice = scan.nextInt();
        if (choice > 0 && choice <= lessons.size()) {
            return lessons.get(choice - 1);
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
