import Coach.Coach;
import Learner.Learner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HJSSSoftware {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public Scanner scanner = new Scanner(System.in);

    private Map<String, Learner> learnerMap;
    private Map<String, Coach> coachMap;

    public HJSSSoftware() {
        learnerMap=new HashMap<>();
        loadLearners(".\\src\\main\\java\\Data\\learner.txt");
        coachMap=new HashMap<>();
        loadCoaches(".\\src\\main\\java\\Data\\coaches.txt");
    }

    private void loadLearners(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Skip the header line
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String gender = parts[2];
                String age = parts[3];
                String emContact = parts[4];
                int currentGrade = Integer.parseInt(parts[5]);
                int coachId = Integer.parseInt(parts[6]);
                learnerMap.put(name, new Learner(id, name, gender, age, emContact,currentGrade, coachId));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
    private void loadCoaches(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Skip the header line
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String gender = parts[2];
                String age = parts[3];
                String email = parts[4];
                
                coachMap.put(name, new Coach(id,name));}
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}

    public void listAllLearners(){
        System.out.println("Learners");
        for(Learner learner:learnerMap.values()){
            System.out.println(learner.getName());
        }
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HJSSSoftware hjssSoftware=new HJSSSoftware();
        HjjsSwim swim= new HjjsSwim(hjssSoftware.learnerMap,hjssSoftware.coachMap);
        boolean running = true;
        System.out.println(ANSI_GREEN+"Hatfield Junior Swimming School System"+ANSI_RESET);
        System.out.print(ANSI_GREEN+"---------------------------------------------------------"+ANSI_RESET);
        while (running) {
            System.out.println(ANSI_YELLOW+"\nMenu:"+ANSI_RESET);
            System.out.println("1. Book a swimming lesson");
            System.out.println("2. Change / cancel a booking");
            System.out.println("3. Attend a swimming lesson");
            System.out.println("4. Monthly learner report");
            System.out.println("5. Monthly coach report");
            System.out.println("6. Register a new learner");
            System.out.println("0. Exit");
            System.out.println("===================Input your option  below  ================== ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    swim.bookSwimmingLesson();

                    break;
                case 2:

                    break;
                case 3:

                     break;
                case 5:
                    
                    
                    hjssSoftware.listAllLearners();
                    break;
                case 6:
                    System.out.println("Enter Full Name");
                    String name = scanner.nextLine();
                    System.out.println("Enter Age");
                    String age = scanner.nextLine();
                    System.out.println("Enter Gender");
                    String gender = scanner.nextLine();
                    System.out.println("Enter Emergency Contact Number");
                    String emContact = scanner.nextLine();
                    System.out.println("Enter Grade Level");
                    int gLevel = Integer.parseInt(scanner.nextLine());
                    int  uniqueId = Integer.parseInt(emContact);
                    Learner newLearner = new Learner(uniqueId,name,gender,age,emContact,gLevel);
                    //Learner learner = new Learner(name, gender, age, emergencyContact, currentGrade);
                    swim.addLearner(newLearner);
                    System.out.println("-------------------------------------------------------");
                    System.out.println("New learner registered successfully!");
                    System.out.println("-------------------------------------------------------");
        
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting Hatfield Junior Swimming School System");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }

        scanner.close();

    }

    
    
  
}