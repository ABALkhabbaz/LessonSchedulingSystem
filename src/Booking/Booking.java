package Booking
public class Booking {
    private Client client;
    private Lesson lesson;

    public Booking(Client client, Lesson lesson) {
        this.client = client;
        this.lesson = lesson;
    }

    public Client getClient() {
        return client;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void confirmBooking() {
        System.out.println("Booking confirmed for client: " + client.getName() + " for lesson: " + lesson.getDiscipline());
    }
}
