package Actors;

import java.util.ArrayList;
import Offerings.Offering;

public class User extends Person {
    private String username;
    private String password;

    public User(String name, String phone, String username, String password) {
        super(name, phone);
        this.username = username;
        this.password = password;
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

    /**
     * Displays the available offerings to the user.
     * @param offerings List of available offerings in the system.
     */
    public void viewsOffering(ArrayList<Offering> offerings) {
        System.out.println(username + " is viewing available offerings:");
        if (offerings.isEmpty()) {
            System.out.println("No offerings available at the moment.");
        } else {
            for (Offering offering : offerings) {
                System.out.println(offering.toString()); // Adjusting to display Offering's details
            }
        }
    }

    @Override
    public void displayRole() {
        System.out.println("Role: User");
    }
}
