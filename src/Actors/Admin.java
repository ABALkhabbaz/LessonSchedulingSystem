package Actors;

import LocationAndSchedule.Location;
import LocationAndSchedule.Schedule;
import Offerings.Lesson;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Admin extends User {

    public Admin(long userId, String name, String phone, LocalDate birthDate, String username, String password) {
        super(userId, name, phone, birthDate, username, password);
    }

    // Method to add a new lesson (renamed from addOffering)
    public Lesson addLesson(ArrayList<Lesson> lessons, Scanner scan) {
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
        Lesson newLesson = new Lesson(lessonId, discipline, instructor, schedule, location, isPrivate);

        // Add the new offering to the list
        lessons.add(newLesson);
    
        System.out.println("New lesson added successfully!");
        System.out.println(newLesson);
        System.out.println("====================================");

        return newLesson;
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
