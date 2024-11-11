package Actors;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import Booking.Booking;
import DAO.DatabaseHandler;
import MySystem.LoginSystem;
import Offerings.Lesson;

public class Client extends User {

    public Client(long userId, String name, String phone, LocalDate birthDate, String username, String password) {
        super(userId, name, phone, birthDate, username, password);
    }

    @Override
    public void displayRole() {
        System.out.println("Client: " + getName());
    }

    public void bookLesson(Lesson lesson) {
        System.out.println("Booking lesson: " + lesson.getDiscipline());
    }

    public void enrollInLesson(DatabaseHandler dbHandler, Scanner scanner, boolean calledFromLegalGuardian) {

        ArrayList<Lesson> lessons = null;

        System.out.println("Enrolling in lesson...");

        try {
            lessons = dbHandler.getAvailableToBookLessons();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (lessons == null || lessons.isEmpty()) {
            System.out.println("No lessons available to book.");
            return;
        }

        System.out.println("Select an offering:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println(i + 1 + ". " + lessons.get(i).toString());
        }

        int choice = new MySystem.MySystem().getUserChoice(1, lessons.size());
        Lesson lesson = lessons.get(choice - 1);

        if (!calledFromLegalGuardian && getAge() < 18) {
            System.out.println("You are under 18 years old. A legal guardian must book for you.");

            return;
        }

        // Ensures that there is no overlap for booking lessons
        if(dbHandler.hasBookingOverlap(this, lesson)) {
            System.out.println("You have already booked a lesson at this time.");
            return;
        }

        Booking booking = dbHandler.insertNewBooking(lesson, this);

        System.out.println("Booking successful. Booking ID: " + booking.getBookingId());

    }

    public void unenrollFromLesson(DatabaseHandler dbHandler, Scanner scanner) {

        System.out.println("Unenrolling from lesson...");

        ArrayList<Booking> bookings = dbHandler.getBookingsByClient(this);

        if (bookings == null || bookings.isEmpty()) {
            System.out.println("No bookings available to cancel.");
            return;
        }

        System.out.println("Select a booking to cancel:");
        for (int i = 0; i < bookings.size(); i++) {
            System.out.println(i + 1 + ". " + bookings.get(i).toString());
        }
        int choice = new MySystem.MySystem().getUserChoice(1, bookings.size());
        Booking booking = bookings.get(choice - 1);

        // Remove booking
        dbHandler.deleteBooking(booking);

        if (booking.getLesson().isPrivate()) {
            dbHandler.updateLessonAvailability(booking.getLesson(), true);
        }
    }

    public void enrollForMinor(DatabaseHandler dbHandler, Scanner scanner) {
          
            System.out.println("Enrolling for a minor...");
    
            System.out.println("Enter the minor's login info:");

            User userMinor = LoginSystem.loginUser(dbHandler, scanner);

            if (userMinor == null) {
                System.out.println("Minor login failed.");
                return;
            }

            if (userMinor.getAge() >= 18) {
                System.out.println("User is not a minor.");
                return;
            }

            if(!(userMinor instanceof Client)) {
                System.out.println("User is not a client.");
                return;
            }

            Client minor = (Client) userMinor;

            minor.enrollInLesson(dbHandler, scanner, true);
    }

}
