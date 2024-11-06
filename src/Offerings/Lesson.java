package Offerings;

import LocationAndSchedule.Location;
import LocationAndSchedule.Schedule;
import Actors.Instructor;
public class Lesson {
    private long lessonId;
    private String discipline;
    private Instructor instructor;
    private Schedule schedule;
    private Location location;
    private boolean isPrivate;

    public Lesson(long lessonId, String discipline, Instructor instructor, Schedule schedule, Location location, boolean isPrivate) {
        this.lessonId = lessonId;
        this.discipline = discipline;
        this.instructor = instructor;
        this.schedule = schedule;
        this.location = location;
        this.isPrivate = isPrivate;
    }

    public long getLessonId() {
        return lessonId;
    }

    public void setLessonId(long lessonId) {
        this.lessonId = lessonId;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    /**
     * Displays detailed information about the lesson.
     */
    public void specificLessonDetails() {
        System.out.println("Lesson Details:");
        System.out.println("LessonId: " + lessonId);
        System.out.println("Discipline: " + discipline);
        System.out.println("Instructor: " + (instructor != null ? instructor.getName() : "No Instructor Assigned"));
        System.out.println("Schedule: " + (schedule != null ? schedule.toString() : "No Schedule Assigned"));
        System.out.println("Location: " + (location != null ? location.toString() : "No Location Assigned"));
        System.out.println("Private Lesson: " + (isPrivate ? "Yes" : "No"));
    }

    @Override
    public String toString() {
        return "Lesson [LessonId=" + lessonId + "Discipline=" + discipline + ", Instructor=" + (instructor != null ? instructor.getName() : "N/A") + "]";
    }
}
