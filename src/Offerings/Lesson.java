public class Lesson {
    private String discipline;
    private Instructor instructor;
    private Schedule schedule;
    private Space space;
    private boolean isPrivate;

    // Constructor
    public Lesson(String discipline, Instructor instructor, Schedule schedule, Space space, boolean isPrivate) {
        this.discipline = discipline;
        this.instructor = instructor;
        this.schedule = schedule;
        this.space = space;
        this.isPrivate = isPrivate;
    }

    // Getter methods
    public String getDiscipline() {
        return discipline;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Space getSpace() {
        return space;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    // Example of a method for lesson-specific details
    public void specificLessonDetails() {
        System.out.println("Lesson details: Discipline - " + discipline + 
                           ", Instructor - " + instructor.getName() + 
                           ", Schedule - " + schedule + 
                           ", Space - " + space.getName() + 
                           ", Is Private - " + (isPrivate ? "Yes" : "No"));
    }
}
