package Actors;

import Offerings.Lesson;

public class Client extends User {

    public Client(long userId, String name, String phone, String username, String password) {
        super(userId, name, phone, username, password);
    }

    public void viewOffering() {
        System.out.println("Viewing available offerings...");
    }

    @Override
    public void displayRole() {
        System.out.println("Client: " + getName());
    }

    public void bookLesson(Lesson lesson) {
        System.out.println("Booking lesson: " + lesson.getDiscipline());
    }
}
