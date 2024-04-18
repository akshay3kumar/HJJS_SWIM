package Learner;

import Lessons.Lesson;
import java.util.ArrayList;
import java.util.List;

public class Learner {
    int learnerId;
    String name;
    String gender;
    String age;
    String emContactNumber;
    int currentGradeLevel;
    int coachId;
    private List<Lesson> bookedLessons = new ArrayList<>();
    private List<Lesson> attendedLessons= new ArrayList<>();
    private List<Lesson> canceledLessons= new ArrayList<>();

    public int getLearnerId() {
        return learnerId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getEmContactNumber() {
        return emContactNumber;
    }

    public int getCurrentGradeLevel() {
        return currentGradeLevel;
    }

    public void setLearnerId(int learnerId) {
        this.learnerId = learnerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmContactNumber(String emContactNumber) {
        this.emContactNumber = emContactNumber;
    }

    public void setCurrentGradeLevel(int currentGradeLevel) {
        this.currentGradeLevel = currentGradeLevel;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    

    public Learner(int learnerId, String name, String gender, String age, String emContactNumber, int currentGradeLevel) {
        this.learnerId = learnerId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.emContactNumber = emContactNumber;
        this.currentGradeLevel = currentGradeLevel;
    }

    public Learner(int learnerId, String name, String gender, String age, String emContactNumber, int currentGradeLevel, int coachId) {
        this.learnerId = learnerId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.emContactNumber = emContactNumber;
        this.currentGradeLevel = currentGradeLevel;
        this.coachId = coachId;
    }

    public List<Lesson> getBookedLessons() {
        return bookedLessons;
    }

    public void setBookedLessons(List<Lesson> bookedLessons) {
        this.bookedLessons = bookedLessons;
    }

    public List<Lesson> getAttendedLessons() {
        return attendedLessons;
    }

    public void setAttendedLessons(List<Lesson> attendedLessons) {
        this.attendedLessons = attendedLessons;
    }

    public List<Lesson> getCanceledLessons() {
        return canceledLessons;
    }

    public void setCanceledLessons(List<Lesson> canceledLessons) {
        this.canceledLessons = canceledLessons;
    }

    public boolean hasDuplicateBooking(int week, String day, String timeSlot, int grade) {
    for (Lesson lesson : bookedLessons) {
        if (lesson.getWeek() == week
                && lesson.getDay().equals(day)
                && lesson.getTime().equals(timeSlot)
                && lesson.getGradeLevel() == grade) {
            return true; // Found a duplicate booking
        }
    }
    return false; // No duplicate booking found
}

    public void addBookedLesson(Lesson lesson) {
    bookedLessons.add(lesson);
}


    
}
