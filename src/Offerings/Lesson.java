package Offerings;

import Actors.Instructor;
import LocationAndSchedule.*;

public class Lesson {
    private String discipline;
    private Instructor instructor;
    private Schedule schedule;
    private Location location;
    private boolean isPrivate;

    // Constructor
    public Lesson(String discipline, Instructor instructor, Schedule schedule, Location location, boolean isPrivate) {
        this.discipline = discipline;
        this.instructor = instructor;
        this.schedule = schedule;
        this.location = location;
        this.isPrivate = isPrivate;
    }

    // Getter methods
    public String getDiscipline() {
        return discipline;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    // Method to display specific lesson details
    public void specificLessonDetails() {
        System.out.println("Lesson Details:");
        System.out.println("Discipline: " + discipline);
        System.out.println("Instructor: " + (instructor != null ? instructor.getName() : "N/A"));
        System.out.println("Schedule: " + (schedule != null ? schedule.toString() : "N/A"));
        System.out.println("Location: " + (location != null ? location.getName() : "N/A"));
        System.out.println("Is Private: " + (isPrivate ? "Yes" : "No"));
    }

    // toString method for easy display
    @Override
    public String toString() {
        return "Lesson [Discipline: " + discipline + 
               ", Instructor: " + (instructor != null ? instructor.getName() : "N/A") +
               ", Schedule: " + (schedule != null ? schedule.toString() : "N/A") +
               ", Location: " + (location != null ? location.getName() : "N/A") +
               ", Is Private: " + (isPrivate ? "Yes" : "No") + "]";
    }
}
