package main;
import Coach.Coach;
import Coach.Coach;
import Learner.Learner;
import Lessons.Lesson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public HjjsSwim() {
    }
    

    public HjjsSwim(Map<String, Learner> learnerMap, Map<String, Coach> coachMap) {
        this.coaches = new ArrayList<Coach>(coachMap.values());
        this.learners = new ArrayList<Learner>(learnerMap.values());
        this.lessons = new ArrayList<>();
        defaultLessons();
       }
    public void defaultLessons()
    {
      InputStream inputStream = HjjsSwim.class.getClassLoader().getResourceAsStream("lesson.txt");
    if (inputStream == null) {
        System.err.println("lessons.txt not found in the classpath.");
        return;
    }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
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
        Learner learner = findLearner(learnerName);
        if (learner == null) {
            System.out.println("Learner not registered");
            return;
        }
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
        System.out.println("You are not Eligible for this Grade ...");
        return;
    }
    
    // Check for duplicate booking
    if (learner.hasDuplicateBooking(sel_week, sel_day, sel_slot, sel_grade)) {
        System.out.println("Duplicate booking not allowed ...");
        return;
    }
    
    // Find the lesson
    Optional<Lesson> lessonOptional = findLesson(sel_week, sel_day, sel_slot, sel_grade);
    if (lessonOptional.isEmpty()) {
        System.out.println("Lesson Not Found !!!!!!");
    } else {
        Lesson lesson = lessonOptional.get();
        if (lesson.canAddLearner()) {
            lesson.add_learner(learner);
            learner.addBookedLesson(lesson);
            System.out.println("Booking Successfull");
        } else {
            System.out.println("Max 4 allowed !!!!!!");
            System.out.println("Booking NOT  Successfull");
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

    public void modifyBookings() {
        System.out.println("Enter learner's name:");
        String Name = scanner.nextLine();
        Optional<Learner> learner_Optional = findLearnerOptional(Name);
        if (learner_Optional.isEmpty()) {
            System.out.println(Name + " Not Registered");
            return;
        }

        Learner learner = learner_Optional.get();
        System.out.println("All Booked Lessons of " + Name);
        if (learner.getBooked().isEmpty()) {
            System.out.println(Name + " has no booked Lessons");
            return;
        }
        learner.getBooked().forEach(bookedLesson -> System.out.println(bookedLesson.toString()));
        System.out.println("week of the lesson:");
        int week = scanner.nextInt();
        scanner.nextLine();
        System.out.println("day of the lesson:");
        String day = scanner.nextLine();

        System.out.println("slot of the lesson:");
        String timeSlot = scanner.nextLine();
        Lesson lesson = find_lesson_by_day_time_grade(day, timeSlot, week);
        if (lesson != null) {
            if (lesson.getLearners().contains(learner)) {
                System.out.println("Do you want to change or cancel the booking?");
                System.out.println("1. Change booking");
                System.out.println("2. Cancel booking");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        change_booking(learner, lesson);
                        break;
                    case 2:
                        cancel_booking(learner, lesson);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                System.out.println(Name + " is not booked for this lesson.");
            }
        } else {
            System.out.println("Lesson not found.");
        }
    }

    private Optional<Learner> findLearnerOptional(String Name) {
        return learners.stream()
                .filter(learner -> learner.getName().equals(Name))
                .findFirst();
    }

private Lesson find_lesson_by_day_time_grade(String day, String time_slot, int week) {
    for (Lesson lesson : lessons) {
        if (lesson.getDay().equalsIgnoreCase(day) && lesson.getTime().equalsIgnoreCase(time_slot)
                && lesson.getWeek() == week) {
            return lesson;
        }
    }
    return null;
}
private Lesson find_lesson_by_week_day_time_grade(String day, String time_slot, int week, int grade) {
    return lessons.stream()
            .filter(lesson -> lesson.getDay().equalsIgnoreCase(day) &&
                    lesson.getTime().equalsIgnoreCase(time_slot) &&
                    lesson.getWeek() == week &&
                    lesson.getGradeLevel() == grade)
            .findFirst()
            .orElse(null);
}
private void change_booking(Learner learner, Lesson lesson) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the new week of the lesson:");
    int new_week = scanner.nextInt();
    scanner.nextLine();
    
    System.out.println("Enter the new day of the lesson:");
    String new_day = scanner.nextLine();

    System.out.println("Enter the new time slot of the lesson:");
    String new_time_slot = scanner.nextLine();

    System.out.println("Enter the new grade level of the lesson:");
    int new_grade_level = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    Lesson new_lesson = find_lesson_by_week_day_time_grade(new_day, new_time_slot, new_week, new_grade_level);
    if (new_lesson != null) {
        if (new_lesson.add_learner(learner)) {
            lesson.remove_learner(learner);
            learner.change_booking(lesson, new_lesson);
            System.out.println("Booking changed successfully.");
        } else {
            System.out.println("The new lesson is already full. Booking change failed.");
        }
    } else {
        System.out.println("New lesson not found.");
    }
}

    private void cancel_booking(Learner learner, Lesson lesson) {
        lesson.getLearners().remove(learner);
        learner.cancel_booking(lesson);
        System.out.println("Booking canceled successfully.");
    }

    public void attend_class() {
        System.out.println("Enter learner's name:");
	String name = scanner.nextLine();
        Optional<Learner> learner_Optional = findLearnerOptional(name);
        if (learner_Optional.isEmpty()) {
            System.out.println(name + " Not Registered");
            return;
        }
        Learner l = learner_Optional.get();
        l.getBooked().forEach(bookedLesson 
                -> System.out.println(bookedLesson.toString()));
        System.out.println("Enter the week of the lesson:");
        int week = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the day of the lesson:");
        String day = scanner.nextLine();
        System.out.println("Enter the time slot of the lesson:");
        String timeSlot = scanner.nextLine();
        Lesson lesson = find_lesson_by_day_time_grade(day, timeSlot, week);
        if (lesson == null) {
            System.out.println("Lesson not found.");
            return;
        }

        if (!lesson.getLearners().contains(l)) {
            System.out.println(name + " is not booked for this lesson.");
            return;
        }
        System.out.println(name + " attended the lesson successfully.");
        System.out.println("1: Very dissatisfied \n"
                + " 2: Dissatisfied\n"
                + " 3: Ok\n"
                + " 4: Satisfied\n"
                + " 5: Very Satisfied\n");
        System.out.println("Give the rating to Coach from (1 to 5) ");
        int rating = scanner.nextInt();
        scanner.nextLine();

        Optional<Coach> coach = this.coaches.stream()
                .filter(coach1 -> coach1.getName().equals(lesson.getCoach())).findFirst();
        if (coach.isEmpty()) {
            return;
        }

        try {
            lesson.setRating(rating);
            coach.get().getLessonsTaught().add((Lesson) lesson.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        lesson.getLearners().remove(l);
        l.attend_lesson(lesson);
    }

    public void learner_monthly_repport() {
        System.out.println("Which Month (in number from 1 to 12");
        int month_number = scanner.nextInt();
        scanner.nextLine();
         String week_range = get_week_range_for_month(month_number);
        String[] arr = null;
        try {
            arr = week_range.split("-");
        } catch (Exception e) {
            System.out.println("INVALID MONTH");
            return;
        }
        int start_week = Integer.parseInt(arr[0]);
        int end_week = Integer.parseInt(arr[1]);
        for (Learner learner : learners) {
            System.out.println("Learner: " + learner.getName());
            System.out.println("Learner ID: " + learner.getLearnerId());

            int learnerBookedCount = printLessons("Booked lessons:", learner.getBooked(), start_week, end_week);
            int learnerCanceledCount = printLessons("Canceled lessons:", learner.getCanceled(), start_week, end_week);
            int learnerAttendedCount = printAttendedLessons("Attended lessons:", learner.getAttended(), start_week, end_week);

            System.out.println("Total booked: " + learnerBookedCount);
            System.out.println("Total canceled: " + learnerCanceledCount);
            System.out.println("Total attended: " + learnerAttendedCount);
            System.out.println();
        }

    }

    private static int printLessons(String label, List<Lesson> lessons, int startWeek, int endWeek) {
        System.out.println(label);
        int count = 0;
        if(lessons.isEmpty())
        {
           System.out.println("EMPTY"); 
        }
        else{
        for (Lesson lesson : lessons) {
            if (lesson.getWeek() >= startWeek && lesson.getWeek() <= endWeek) {
                System.out.println(" - " + lesson.getDay() + " " + lesson.getTime());
                count++;
            }
        }
        }
        return count;
    }

    private static int printAttendedLessons(String label, List<Lesson> lessons, int startWeek, int endWeek) {
        System.out.println(label);
        int count = 0;
        for (Lesson lesson : lessons) {
            if (lesson.getWeek() >= startWeek && lesson.getWeek() <= endWeek) {
                System.out.println(" - " + lesson.getDay() + " " + lesson.getTime());
                count++;
            }
        }
        return count;
    }
    public void coach_monthly_report() {
        System.out.println("Which Month (in number from 1 to 12");
        int month_number = scanner.nextInt();
        scanner.nextLine();
        String week_range = get_week_range_for_month(month_number);
        String[] arr = null;
        try {
            arr = week_range.split("-");
        } catch (Exception e) {
            System.out.println("INVALID MONTH");
            return;
        }
        int start_week = Integer.parseInt(arr[0]);
        int end_week = Integer.parseInt(arr[1]);
         for (Coach coach : coaches) {
            System.out.println("Coach: " + coach.getName());
            print_coach_lessons(coach, start_week, end_week);
            System.out.println();
        }
    }
    private static void print_coach_lessons(Coach coach, int start_week, int end_week) {
        int totalRatings = 0;
        int number_of_ratings = 0;

        for (Lesson lesson : coach.getLessonsTaught()) {
            if (lesson.getWeek() >= start_week && lesson.getWeek() <= end_week) {
                System.out.println("Lesson: " + lesson.getDay() + " " + lesson.getTime());
                int rating = lesson.getRating();
                if (rating != -1) { // -1 indicates no rating provided
                    totalRatings += rating;
                    number_of_ratings++;
                    System.out.println(" - Rating: " + rating);
                }
            }
        }

        if (number_of_ratings > 0) {
            double averageRating = calculateAverageRating(totalRatings, number_of_ratings);
            System.out.println("Average Rating: " + averageRating);
        } else {
            System.out.println("No ratings available.");
        }
    }

    private static double calculateAverageRating(int totalRatings, int numberOfRatings) {
        return (double) totalRatings / numberOfRatings;
    }
    public  String get_week_range_for_month(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            return "Invalid month number";
        }
        
        int startWeek, endWeek;
        switch(monthNumber) {
            case 1:
                startWeek = 1; endWeek = 4;
                break;
            case 2:
                startWeek = 5; endWeek = 8;
                break;
            case 3:
                startWeek = 9; endWeek = 12;
                break;
            case 4:
                startWeek = 13; endWeek = 16;
                break;
            case 5:
                startWeek = 17; endWeek = 20;
                break;
            case 6:
                startWeek = 21; endWeek = 24;
                break;
            case 7:
                startWeek = 25; endWeek = 28;
                break;
            case 8:
                startWeek = 29; endWeek = 32;
                break;
            case 9:
                startWeek = 33; endWeek = 36;
                break;
            case 10:
                startWeek = 37; endWeek = 40;
                break;
            case 11:
                startWeek = 41; endWeek = 44;
                break;
            case 12:
                startWeek = 45; endWeek = 48;
                break;
            default:
                return "Invalid month number";
        }
        
        return startWeek + "-" + endWeek;
    }

    public void addLesson(Lesson lesson) {
        if (this.lessons == null) {
            this.lessons = new ArrayList<>();
            this.lessons.add(lesson);
        } else {
            this.lessons.add(lesson);
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
    
}
