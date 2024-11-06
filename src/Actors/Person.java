package Actors;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.DatabaseHandler;
import Offerings.Lesson;

public class Person {
    
    public Person() {}

    // Display available offerings to the public or any user type
    public static void displayAvailableLessons(DatabaseHandler dbHandler) {

        ArrayList<Lesson> lessons = new ArrayList<Lesson>();
        try {
            lessons = dbHandler.getLessons();
        } catch(SQLException e) {
            e.printStackTrace();
            return;
        }
        
        if (lessons.isEmpty()) {
            System.out.println("No offerings available at the moment.");
        } else {
            System.out.println("Available Offerings:");
            for (Lesson lesson : lessons) {
                if(lesson.getInstructor() == null) continue; // Only display lessons with assigned instructors to public 
                System.out.println("- " + lesson.getDiscipline() + " by "
                        + lesson.getInstructor().getName());
            }
        }
    }
}
