package Booking;

import java.util.ArrayList;

import Actors.Client;
import Offerings.Lesson;

public class Booking {
    private long bookingId;
    private ArrayList<Client> clients;
    private Lesson lesson;

    public Booking(long bookingId,Lesson lesson) {
        this.bookingId = bookingId;
        this.lesson = lesson;
        this.clients = new ArrayList<Client>();
    }

    public long getBookingId(){
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void associateClient(Client client) {
        clients.add(client);
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void confirmBooking() {
        System.out.println("Booking confirmed for client(s): ");
        for (Client client : clients) {
            System.out.println(client.getName());
        }
        System.out.println("for lesson: " + lesson.getDiscipline());
    }
}
