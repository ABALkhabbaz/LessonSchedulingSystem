package LocationAndSchedule;

import java.sql.Time;
import java.util.Date;

public class Schedule {
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private String day;

    // Constructor
    public Schedule(Date startDate, Date endDate, Time startTime, Time endTime, String day) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    // Getter methods
    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }

    // Setter methods
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    public void setDay(String day) {
        this.day = day;
    }
    


    // Example of a method to display schedule details
    @Override
    public String toString() {
        return ("Schedule: Start Date - " + startDate + 
                           ", End Date - " + endDate + 
                           ", Start Time - " + startTime + 
                           ", End Time - " + endTime + 
                           ", Day - " + day);
    }
}
