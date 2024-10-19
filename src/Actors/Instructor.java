package Actors;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {
    private ArrayList<String> availableCities;
    private String specialization;

    public Instructor(String name, String phone, int age, String username, String password, ArrayList<String> availableCities, String specialization) {
        super(name, phone, age, username, password);
        this.availableCities = availableCities;
        this.specialization = specialization;
    }

    public void selectLessons() {
        System.out.println("Selecting lessons...");
    }

    public List<String> getAvailableCities() {
        return availableCities;
    }

    public String getSpecialization() {
        return specialization;
    }
}
