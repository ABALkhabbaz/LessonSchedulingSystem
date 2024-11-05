package Offerings;

import Actors.Instructor;
import LocationAndSchedule.Location;

public class Offering {
    private Lesson lesson;

    public Offering(Lesson lesson) {
        this.lesson = lesson;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    /**
     * Displays details of the offering.
     */
    public void displayOfferingDetails() {
        if (lesson != null) {
            System.out.println("Offering Details:");
            System.out.println("Discipline: " + lesson.getDiscipline());
            System.out.println("Instructor: " + lesson.getInstructor().getName());
            System.out.println("Schedule: " + lesson.getSchedule().toString());
            System.out.println("Location: " + lesson.getLocation().toString());
            System.out.println("Private Lesson: " + (lesson.isPrivate() ? "Yes" : "No"));
        } else {
            System.out.println("No lesson associated with this offering.");
        }
    }

    @Override
    public String toString() {
        return "Offering: " + (lesson != null ? lesson.getDiscipline() : "No Lesson Assigned");
    }
}
