
public class MySystem {

    private static void displayWelcomeMenu(){
        System.out.println("====================================");
        System.out.println("Welcome to the Lesson Scheduling System!");
        System.out.println("====================================");
    }

    private static int displayMenuOption(Person user){

        System.out.println("Menu Options: ");

        if()

        System.out.println("1. Display User Information");
        System.out.println("2. Update User Information");
        System.out.println("3. Exit");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(System.console().readLine());

        return choice;
    }

    public static void main(String[] args) {

        displayWelcomeMenu();

        // Create a new instance of Person
        Person user = new Person();

        // Display the menu options
        displayMenuOption(user);



    }
}
