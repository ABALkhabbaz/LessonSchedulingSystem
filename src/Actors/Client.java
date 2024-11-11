package Actors;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import Booking.Booking;
import DAO.DatabaseHandler;
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

    public void enrollInLesson(DatabaseHandler dbHandler, Scanner scanner) {
        
        ArrayList<Lesson> lessons = null;

        System.out.println("Enrolling in lesson...");

        try {
            lessons = dbHandler.getAvailableToBookLessons();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(lessons == null || lessons.isEmpty()) {
            System.out.println("No lessons available to book.");
            return;
        }

        System.out.println("Select an offering:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println(i + 1 + ". " + lessons.get(i).toString());
        }
        int choice = new MySystem.MySystem().getUserChoice(1, lessons.size());
        Lesson lesson = lessons.get(choice - 1);
        

        Booking booking = dbHandler.insertNewBooking(lesson, this);
        // TODO: NOT WORKING PROPERLY
        
        dbHandler.updateLessonAvailability(lesson, false);
        System.out.println("Booking successful. Booking ID: " + booking.getBookingId());

    }

    public void unenrollFromLesson(DatabaseHandler dbHandler, Scanner scanner) {
        
        System.out.println("Unenrolling from lesson...");

        ArrayList<Booking> bookings = dbHandler.getBookingsByClient(this);

        if(bookings == null || bookings.isEmpty()) {
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

        if(booking.getLesson().isPrivate()) {
            dbHandler.updateLessonAvailability(booking.getLesson(), true);
        }

    }
}
