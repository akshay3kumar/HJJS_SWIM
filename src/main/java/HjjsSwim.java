
import Coach.Coach;
import Learner.Learner;
import Lessons.Lesson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;


public class HjjsSwim {

    Scanner scanner = new Scanner(System.in);
    private List<Lesson> lessons;
    private List<Coach> coaches;
    private List<Learner> learners;

    HjjsSwim(Map<String, Learner> learnerMap, Map<String, Coach> coachMap) {
        this.coaches = new ArrayList<Coach>(coachMap.values());
        this.learners = new ArrayList<Learner>(learnerMap.values());
        this.lessons = new ArrayList<>();
        defaultLessons();
       }
    public void defaultLessons()
    {
      String filename = ".\\src\\main\\java\\Data\\lesson.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int grade;
            int week;
            reader.readLine();
            // Read each line from the file until the end
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                String[] l = line.split(",");
                week = Integer.parseInt(l[0]);
                grade = Integer.parseInt(l[3]);
                int coachId = Integer.parseInt(l[4]);
                Optional<Coach> coach = coaches.stream().filter(x-> x.getId()==coachId).findFirst();
                Lesson les = new Lesson(l[1], l[2], grade, coach.get().getName());
                les.setWeek(week);
                lessons.add(les);
            }

            // Close the reader
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }  
    }
    public  void bookSwimmingLesson() {
        System.out.println("Enter your name:");
        String learnerName = scanner.nextLine();
//        Learner learner = swimmingSystem.findLearner(learnerName);
//        if (learner == null) {
//            System.out.println("Learner not registered");
//            return;
//        }
        System.out.println("---------------TimeTable available in 3 ways -------------------------------");
        System.out.println("1. specifying day");
        System.out.println("2. specifying grade level");
        System.out.println("3. specifying coach");
        System.out.println("----------------------------------------------");
        System.out.println("Select choice from 1 to 3:");
        int filterOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (filterOption == 1) {
            filterAndDisplayTimetable(learnerName, "Day");
        } else if (filterOption == 2) {
            filterAndDisplayTimetableByGrade( learnerName);
        } else if (filterOption == 3) {
            filterAndDisplayTimetable(learnerName, "coach");
        } else {
            System.out.println("Invalid filter option.");
        }
    }

    private  void filterAndDisplayTimetable(String learnerName, String filterType) {
        System.out.println("Input for " + filterType + ":");
        String filterValue = scanner.nextLine();
        displayTimetable(filterType, filterValue);
        askForBooking( learnerName);
    }

    private  void filterAndDisplayTimetableByGrade(String learnerName) {
        System.out.println("Inout for grade level (1 to 5):");
        int gradeLevel = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        displayTimetable("Grade level", String.valueOf(gradeLevel));
        askForBooking(learnerName);
    }

   public void displayTimetable(String day, String filterValue) {
    String filterType = "";
    switch(day.toLowerCase()) {
        case "day":
            filterType = "Day";
            break;
        case "grade level":
            filterType = "Grade Level";
            break;
        case "coach":
            filterType = "Coach";
            break;
        default:
            System.out.println("Invalid filter type.");
            return;
    }

    System.out.println("Timetable for " + filterType + " | Filter: " + filterValue);
    System.out.println("---------------------------------------------------------------------");
    System.out.println("Slot \t\t Day\tWeek\t\tGrade\tCoach\tVacant");
    System.out.println("---------------------------------------------------------------------");

    for (Lesson lesson : lessons) {
        boolean match = false;
        if (day.equalsIgnoreCase("day")) {
            match = lesson.getDay().equalsIgnoreCase(filterValue);
        } else if (day.equalsIgnoreCase("grade level")) {
            match = lesson.getGradeLevel() == Integer.parseInt(filterValue);
        } else if (day.equalsIgnoreCase("coach")) {
            match = lesson.getCoach().equalsIgnoreCase(filterValue);
        }

        if (match) {
            System.out.println(lesson.getTime()
                    + "\t" + lesson.getDay()
                    + "\t\t" + lesson.getWeek()
                    + "\t  " + lesson.getGradeLevel()
                    + "\t  " + lesson.getCoach()
                    + "\t   " + (4 - lesson.getLearners().size()));
        }
    }
    System.out.println("------------------------------------------------------------");
}
public Learner findLearner(String learnerName) {
        for (Learner learner : learners) {
            if (learner.getName().equalsIgnoreCase(learnerName)) {
                return learner;
            }
        }
        return null;
    }

   public void askForBooking( String learnerName) {
    Learner learner = findLearner(learnerName);
    
    // Get booking details from the user
    int sel_week = getInputInt(scanner, "Enter Week number:");
    scanner.nextLine();
    String sel_day = getInputString(scanner, "Enter Day:");
    String sel_slot = getInputString(scanner, "Enter TimeSlot:");
    int sel_grade = getInputInt(scanner, "Enter Grade:");
    scanner.nextLine();
    
    // Check if learner is eligible for the grade
    if (learner.getCurrentGradeLevel() < sel_grade - 1) {
        System.out.println("-------------------------------------------------------");
        System.out.println("You are not Eligible for this Grade ...");
        System.out.println("-------------------------------------------------------");
        return;
    }
    
    // Check for duplicate booking
    if (learner.hasDuplicateBooking(sel_week, sel_day, sel_slot, sel_grade)) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Duplicate booking not allowed ...");
        System.out.println("-------------------------------------------------------");
        return;
    }
    
    // Find the lesson
    Optional<Lesson> lessonOptional = findLesson(sel_week, sel_day, sel_slot, sel_grade);
    if (lessonOptional.isEmpty()) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Lesson Not Found !!!!!!");
        System.out.println("-------------------------------------------------------");
    } else {
        Lesson lesson = lessonOptional.get();
        if (lesson.canAddLearner()) {
            lesson.addLearner(learner);
            learner.addBookedLesson(lesson);
            System.out.println("-------------------------------------------------------");
            System.out.println("Booking Successfull");
            System.out.println("-------------------------------------------------------");
        } else {
            System.out.println("-------------------------------------------------------");
            System.out.println("Max 4 allowed !!!!!!");
            System.out.println("Booking NOT  Successfull");
            System.out.println("-------------------------------------------------------");
        }
    }
}

// Helper method to get integer input
private int getInputInt(Scanner scanner, String prompt) {
    System.out.println(prompt);
    return scanner.nextInt();
}

// Helper method to get string input
private String getInputString(Scanner scanner, String prompt) {
    System.out.println(prompt);
    return scanner.nextLine();
}

// Helper method to find a lesson
private Optional<Lesson> findLesson(int week, String day, String timeSlot, int grade) {
    return lessons.stream()
            .filter(lesson -> (lesson.getWeek() == week
                    && lesson.getDay().equals(day)
                    && lesson.getTime().equals(timeSlot)
                    && lesson.getGradeLevel() == grade))
            .findFirst();
}
public void addLearner(Learner learner) {
        learners.add(learner);
    }

}
