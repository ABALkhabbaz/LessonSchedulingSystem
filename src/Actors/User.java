package Actors;

import java.time.LocalDate;
import java.util.ArrayList;

import Offerings.Lesson;

public abstract class User extends Person {
    
    private long userId;
    private String name;
    private String phone;
    private LocalDate birthDate;
    private String username; 
    private String password;

    public User(long userId, String name, String phone, LocalDate birthDate, String username, String password) {
        super();
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.birthDate = birthDate;
        this.username = username;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate () {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    /**
     * Displays the available offerings to the user.
     * @param offerings List of available offerings in the system.
     */
    public void viewsOffering(ArrayList<Lesson> lessons) {
        System.out.println(username + " is viewing available offerings:");
        if (lessons.isEmpty()) {
            System.out.println("No offerings available at the moment.");
        } else {
            for (Lesson lesson : lessons) {
                System.out.println(lesson.toString()); // Adjusting to display Offering's details
            }
        }
    }
    
    public void displayRole() {
        System.out.println("Role: User");
    }
}
