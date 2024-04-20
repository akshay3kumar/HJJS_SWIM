/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lessons;

import Learner.Learner;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author akuma797
 */
public class Lesson implements Cloneable{
    private List<Learner> learners;
    private int rating;
    private int gradeLevel;
    private String coach;
    private int capacity;
    private String day;
    private int week;
    private String time;

    public Lesson(String day, String time, int gradeLevel, String coach) {
        this.day = day;
        this.time = time;
        this.gradeLevel = gradeLevel;
        this.coach = coach;
        this.learners = new ArrayList<>();
    }

    public List<Learner> getLearners() {
        return learners;
    }

    public void setLearners(List<Learner> learners) {
        this.learners = learners;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

   public boolean canAddLearner() {
    return learners.size() < 4;
}

    public boolean add_learner(Learner learner) {
    if (learners.size() < 4) {
            learners.add(learner);
            return true;
        }
        return false;
}

    public void remove_learner(Learner learner) {
    this.getLearners().remove(learner);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    @Override
    public String toString() {
        return "Lesson \n" +
                "day='" + day + '\'' +"\n"+
                ", week=" + week +"\n"+
                ", time='" + time + '\'' +"\n"+
                ", gradeLevel=" + gradeLevel +"\n"+
                '}';
    }
    
    
}
