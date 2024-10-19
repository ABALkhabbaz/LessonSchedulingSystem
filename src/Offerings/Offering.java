package Offerings;

import Actors.Instructor;
import LocationAndSchedule.Location;

public class Offering {
    private Lesson lesson;

    public Offering(Lesson lesson) {
        this.lesson = lesson;
    }

    public Instructor getInstructor() {
        return lesson.getInstructor();
    }
    
    public void setInstructor(Instructor instructor) {
        this.lesson.setInstructor(instructor);
    }
    
    public Location getLocation(){
        return this.lesson.getLocation();
    }
    @Override
    public String toString(){
        return this.lesson.toString();
    }
    
    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

}
