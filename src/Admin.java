public class Admin extends Person {

    public Admin(String name, String phone) {
        super(name, phone);
    }

    public void manageOfferings() {
        System.out.println("Managing offerings...");
    }

    @Override
    public void displayRole() {
        System.out.println("Admin: " + name);
    }
}
