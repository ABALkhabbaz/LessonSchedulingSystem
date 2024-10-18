import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {

    private String day;

    private LocalTime startTime;
    private LocalTime endTime;

    private LocalDate startDate;
    private LocalDate endDate;

    // Constructors
    public Schedule(){}

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

    public LocalDate getStartDate(){
        return startDate;
    }

    public LocalDate getEndDate(){
        return endDate;
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

    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }   
}
