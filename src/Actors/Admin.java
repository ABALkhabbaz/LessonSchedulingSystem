package Actors;

import LocationAndSchedule.Location;
import LocationAndSchedule.Schedule;
import Offerings.Lesson;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

import DAO.DatabaseHandler;

public class Admin extends User {

    public Admin(long userId, String name, String phone, LocalDate birthDate, String username, String password) {
        super(userId, name, phone, birthDate, username, password);
    }

    // Method to add a new lesson (renamed from addOffering)
    public Lesson addLesson(DatabaseHandler dbHandler, Scanner scan) {
        System.out.println("====================================");
        System.out.println("Adding a New Lesson");

        // Collect lesson details from the admin
        System.out.print("Enter discipline: ");
        String discipline = scan.nextLine();

        Instructor instructor = null;

        // Parse start date
        Date startDate = null;
        while (startDate == null) {
            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDateStr = scan.nextLine();
            startDate = parseDate(startDateStr);
            if (startDate == null) {
                System.err.println("Invalid date format. Please try again.");
            }
        }

        // Parse end date
        Date endDate = null;
        while (endDate == null) {
            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDateStr = scan.nextLine();
            endDate = parseDate(endDateStr);
            if (endDate == null) {
                System.err.println("Invalid date format. Please try again.");
            }
        }

        // Parse start time
        Time startTime = null;
        while (startTime == null) {
            System.out.print("Enter start time (HH:MM): ");
            String startTimeStr = scan.nextLine();
            startTime = parseTime(startTimeStr);
            if (startTime == null) {
                System.err.println("Invalid time format. Please try again.");
            }
        }

        // Parse end time
        Time endTime = null;
        while (endTime == null) {
            System.out.print("Enter end time (HH:MM): ");
            String endTimeStr = scan.nextLine();
            endTime = parseTime(endTimeStr);
            if (endTime == null) {
                System.err.println("Invalid time format. Please try again.");
            }
        }

        System.out.print("Enter day of the week: ");
        String day = scan.nextLine();

        // Create a new schedule object
        Schedule schedule = new Schedule(startDate, endDate, startTime, endTime, day);

        // Collect location details
        System.out.print("Enter location name: ");
        String locationName = scan.nextLine();

        System.out.print("Enter city: ");
        String city = scan.nextLine();

        System.out.print("Enter province: ");
        String province = scan.nextLine();

        System.out.print("Enter address: ");
        String address = scan.nextLine();

        // Create a new location object
        Location location = new Location(locationName, city, province, address);

        System.out.print("Is the lesson private? (yes/no): ");
        boolean isPrivate = scan.nextLine().equalsIgnoreCase("yes");

        // Create a new lesson object
        long lessonId = 0; // Use lessonId = 0 for now
        Lesson newLesson = new Lesson(lessonId, discipline, instructor, schedule, location, isPrivate, true);

        // Check if schedule overlaps with existing lessons
        boolean hasOverlap = false;
        try {
            hasOverlap = dbHandler.hasLessonOverlap(newLesson);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(hasOverlap) {
            System.err.println("Lesson schedule overlaps with existing lesson. Please try again.");
            return null;
        }
        
        try {
            dbHandler.insertLesson(newLesson);
        } catch (Exception e) {
            System.err.println("Failed to add new lesson. Please try again.");
            return null;
        }
        System.out.println("New lesson added successfully!");
        System.out.println(newLesson);
        System.out.println("====================================");

        return newLesson;
    }

    // Method to get all lessons
    public ArrayList<Lesson> getLessons(DatabaseHandler dbHandler) {
        ArrayList<Lesson> lessons = new ArrayList<Lesson>();
        try {
            lessons = dbHandler.getAllLessons(this);
        } catch (Exception e) {
            System.err.println("Failed to retrieve lessons. Please try again.");
        }
        return lessons;
    }
    
    // Display allLessons
    public void displayAllLessons(DatabaseHandler dbHandler) {

        ArrayList<Lesson> lessons = getLessons(dbHandler);

        System.out.println("====================================");
        System.out.println("All Lessons");
        for (Lesson lesson : lessons) {
            System.out.println(lesson);
        }
        System.out.println("====================================");
    }
    
    // Method to update a lesson
    public void updateLesson(DatabaseHandler dbHandler, Scanner scan) {
        System.out.println("====================================");
        System.out.println("Updating a Lesson");

        // Get all lessons
        ArrayList<Lesson> lessons = getLessons(dbHandler);

        // Display all lessons
        System.out.println("Select a lesson to update:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println((i + 1) + ". " + lessons.get(i));
        }

        // Get the lesson index to update
        int lessonIndex = -1;
        while (lessonIndex < 0 || lessonIndex >= lessons.size()) {
            System.out.print("Enter lesson number: ");
            lessonIndex = scan.nextInt() - 1;
            scan.nextLine(); // Consume newline character
            if (lessonIndex < 0 || lessonIndex >= lessons.size()) {
                System.err.println("Invalid lesson number. Please try again.");
            }
        }

        // Get the lesson to update
        Lesson lesson = lessons.get(lessonIndex);

        // Collect lesson details from the admin
        System.out.print("Enter discipline [" + lesson.getDiscipline() + "]: ");
        String discipline = scan.nextLine();
        if (!discipline.isEmpty()) {
            lesson.setDiscipline(discipline);
        }

        // Parse start date
        Date startDate = null;
        while (startDate == null) {
            System.out.print("Enter start date (YYYY-MM-DD) [" + lesson.getSchedule().getStartDate() + "]: ");
            String startDateStr = scan.nextLine();
            if (startDateStr.isEmpty()) {
                startDate = lesson.getSchedule().getStartDate();
            } else {
                startDate = parseDate(startDateStr);
                if (startDate == null) {
                    System.err.println("Invalid date format. Please try again.");
                }
            }
        }

        // Parse end date
        Date endDate = null;
        while (endDate == null) {
            System.out.print("Enter end date (YYYY-MM-DD) [" + lesson.getSchedule().getEndDate() + "]: ");
            String endDateStr = scan.nextLine();
            if (endDateStr.isEmpty()) {
                endDate = lesson.getSchedule().getEndDate();
            } else {
                endDate = parseDate(endDateStr);
                if (endDate == null) {
                    System.err.println("Invalid date format. Please try again.");
                }
            }
        }

        // Parse start time
        Time startTime = null;
        while (startTime == null) {
            System.out.print("Enter start time (HH:MM) [" + lesson.getSchedule().getStartTime() + "]: ");
            String startTimeStr = scan.nextLine();
            if (startTimeStr.isEmpty()) {
                startTime = lesson.getSchedule().getStartTime();
            } else {
                startTime = parseTime(startTimeStr);
                if (startTime == null) {
                    System.err.println("Invalid time format. Please try again.");
                }
            }
        }

        // Parse end time
        Time endTime = null;
        while (endTime == null) {
            System.out.print("Enter end time (HH:MM) [" + lesson.getSchedule().getEndTime() + "]: ");
            String endTimeStr = scan.nextLine();
            if (endTimeStr.isEmpty()) {
                endTime = lesson.getSchedule().getEndTime();
            } else {
                endTime = parseTime(endTimeStr);
                if (endTime == null) {
                    System.err.println("Invalid time format. Please try again.");
                }
            }
        }

        System.out.print("Enter day of the week [" + lesson.getSchedule().getDay() + "]: ");
        String day = scan.nextLine();
        if (!day.isEmpty()) {
            lesson.getSchedule().setDay(day);
        }

        // Collect location details
        System.out.print("Enter location name [" + lesson.getLocation().getName() + "]: ");
        String locationName = scan.nextLine();
        if (!locationName.isEmpty()) {
            lesson.getLocation().setName(locationName);
        }

        System.out.print("Enter city [" + lesson.getLocation().getCity() + "]: ");
        String city = scan.nextLine();
        if (!city.isEmpty()) {
            lesson.getLocation().setCity(city);
        }

        System.out.print("Enter province [" + lesson.getLocation().getProvince() + "]: ");
        String province = scan.nextLine();
        if (!province.isEmpty()) {
            lesson.getLocation().setProvince(province);
        }

        System.out.print("Enter address [" + lesson.getLocation().getAddress() + "]: ");
        String address = scan.nextLine();
        if (!address.isEmpty()) {
            lesson.getLocation().setAddress(address);
        }

        System.out.print("Is the lesson private? (yes/no) [" + (lesson.isPrivate() ? "yes" : "no") + "]: ");
        String isPrivateStr = scan.nextLine();
        if (!isPrivateStr.isEmpty()) {
            lesson.setPrivate(isPrivateStr.equalsIgnoreCase("yes"));
        }

        // Update the lesson
        try {
            dbHandler.updateLesson(lesson);
        } catch (Exception e) {
            System.err.println("Failed to update lesson. Please try again.");
            return;
        }

        System.out.println("Lesson updated successfully!");
    }



    // Helper method to parse date from string
    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    // Helper method to parse time from string
    private Time parseTime(String timeStr) {
        try {
            return Time.valueOf(timeStr + ":00"); // Append seconds for Time format
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
