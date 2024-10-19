import java.util.List;

public class Instructor extends Person {
    private List<String> availableCities;
    private String specialization;

    public Instructor(String name, String phone, List<String> availableCities, String specialization) {
        super(name, phone);
        this.availableCities = availableCities;
        this.specialization = specialization;
    }

    public void selectLessons() {
        System.out.println("Selecting lessons...");
    }

    @Override
    public void displayRole() {
        System.out.println("Instructor: " + name);
    }

    public List<String> getAvailableCities() {
        return availableCities;
    }

    public String getSpecialization() {
        return specialization;
    }
}
