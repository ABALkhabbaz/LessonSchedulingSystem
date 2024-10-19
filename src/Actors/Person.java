package Actors;

import Offerings.Offering;
import java.util.ArrayList;

public class Person {

    private String name;
    private String phone;
    private int age;
    
    // Clients and non clients function to view available Offerings
    public static void displayAvailableOfferings(ArrayList<Offering> offerings) {
        // Prints offerings with instuctors assigned
        System.out.println("====================================");
        System.out.println("Available Offerings:");
    
        // Check if the list is empty
        if (offerings.isEmpty()) {
            System.out.println("No offerings available at the moment.");
        } else {
            // Iterate through the offerings list and display each one
            for (int i = 0; i < offerings.size(); i++) {
                Offering offering = offerings.get(i);

                // skips if instructor is null
                if(offering.getInstructor() == null) continue;

                System.out.println(i + ". " + offering.toString()); // Assuming Offering class has a toString() method
            }
        }
    
        System.out.println("====================================");
    }


    public Person(){
        name = "Default Name";
        age = 0;
    }

    public Person(String name, String phone, int age){
        this.name = name;
        this.phone = phone;
        this.age = age;
    }

    public void display(){
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Phone: " + phone);
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }

    public int getAge(){
        return age;
    }
}