package Offerings;

import Actors.Instructor;

public class Offering {
    private Lesson lesson;

    public Offering(Lesson lesson) {
        this.lesson = lesson;
    }

    public Instructor getInstructor() {
        return lesson.getInstructor();
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
