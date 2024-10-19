package Actors;

public class Client extends User {

    public Client(String name, String phone) {
        super(name, phone);
    }

    public void viewOffering() {
        System.out.println("Viewing available offerings...");
    }

    @Override
    public void displayRole() {
        System.out.println("Client: " + name);
    }

    public void bookLesson(Lesson lesson) {
        System.out.println("Booking lesson: " + lesson.getDiscipline());
    }
}
