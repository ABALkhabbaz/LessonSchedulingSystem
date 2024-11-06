package Booking;

import Actors.Client;
import Offerings.Lesson;

public class Booking {
    private long bookingId;
    private Client client;
    private Lesson lesson;

    public Booking(long bookingId,Lesson lesson, Client client) {
        this.bookingId = bookingId;
        this.lesson = lesson;
        this.client = client;
    }

    public long getBookingId(){
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void confirmBooking() {
        System.out.println("Booking confirmed for client: " + client.getName() + "for lesson: " + lesson.getDiscipline());
    }
}
