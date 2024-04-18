package Coach;

import Lessons.Lesson;
import java.util.ArrayList;
import java.util.List;

public class Coach {
    private int id;
    private String name;
    private String gender;
    private int age;
    private String email;
    private List<Lesson> lessonsTaught;

    public Coach(int id, String name) {
        this.id = id;
        this.name = name;
        this.lessonsTaught = new ArrayList<>();}

    
       

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Coach(int id, String name, String gender, int age, String email) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public List<Lesson> getLessonsTaught() {
        return lessonsTaught;
    }

    public void setLessonsTaught(List<Lesson> lessonsTaught) {
        this.lessonsTaught = lessonsTaught;
    }
    
}
