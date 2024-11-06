package Actors;

import java.util.ArrayList;
import Offerings.Lesson;

public class Person {
    
    public Person() {}

    // Static method to display available offerings
    public static void displayAvailableOfferings(ArrayList<Lesson> lessons) {
        System.out.println("Available Offerings:");
        for (Lesson lesson : lessons) {
            System.out.println("- " + lesson.getDiscipline());
        }
    }
}
