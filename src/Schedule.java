import java.time.LocalTime;

public class Schedule {

    private String day;
    private LocalTime startTime;
    private LocalTime endTime;


    // Constructors
    public Schedule(String day, LocalTime startTime, LocalTime endTime){
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void display(){
        System.out.println("Day: " + day);
        System.out.println("Start Time: " + startTime);
        System.out.println("End Time: " + endTime);
    }

    // Getters
    public String getDay(){
        return day;
    }
    public LocalTime getStartTime(){
        return startTime;
    }

    public LocalTime getEndTime(){
        return endTime;
    }

    // Setters
    public void setDay(String day){
        this.day = day;
    }

    public void setStartTime(LocalTime startTime){
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime){
        this.endTime = endTime;
    }

    

}
