package Actors;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import MySystem.MySystem;

import DAO.DatabaseHandler;
import Offerings.Lesson;

public class Instructor extends User {
    private List<String> availableCities;
    private String specialization;

    public Instructor(long userId, String name, String phone, LocalDate birthDate, String username, String password,
            String specialization, ArrayList<String> availableCities) {
        super(userId, name, phone, birthDate, username, password);
        this.availableCities = availableCities;
        this.specialization = specialization;
    }

    public Lesson selectLesson(DatabaseHandler dbHandler, Scanner scan) {

        ArrayList<Lesson> lessons = null;
        Lesson lesson = null;
        try {
            lessons = dbHandler.getAvailableLessonsForInstructor(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (lessons == null || lessons.isEmpty()) {
            System.out.println("No lessons available.");
            return null;
        }

        System.out.println("Select an offering:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println(i + 1 + ". " + lessons.get(i).toString());
        }
        int choice = new MySystem().getUserChoice(1, lessons.size());
        lesson = lessons.get(choice - 1);

        lesson.setInstructor(this);
        dbHandler.updateInstructorOfLesson(lesson, this);
        return lesson;
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

    public long getInstructorId() {
        return getUserId();
    }

    @Override
    public void displayRole() {
        System.out.println("Instructor: " + getName());
    }

    public void updateLesson(DatabaseHandler dbHandler, Scanner scanner) {
        ArrayList<Lesson> lessons = null;
        Lesson lesson = null;
        
        lessons = dbHandler.getLessonsByInstructor(this);

        if (lessons == null || lessons.isEmpty()) {
            System.out.println("No lessons available.");
            return;
        }

        System.out.println("Select an offering to drop:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println(i + 1 + ". " + lessons.get(i).toString());
        }
        int choice = new MySystem().getUserChoice(1, lessons.size());
        lesson = lessons.get(choice - 1);

        // Remove instructor from lesson
        lesson.setInstructor(null);
        dbHandler.updateInstructorOfLesson(lesson, null);
    }
}
