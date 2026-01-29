import java.util.*;
import java.io.*;

class Student {
    String name;
    int[] grades;
    String[] subjects;

    Student(String name, String[] subjects, int[] grades) {
        this.name = name;
        this.subjects = subjects;
        this.grades = grades;
    }

    // Average for this student
    double getAverage() {
        int sum = 0;
        for (int g : grades)
            sum += g;
        return (double) sum / grades.length;
    }

    // Display student info neatly
    void display() {
        System.out.printf("%-15s", name);
        for (int g : grades)
            System.out.printf("%-10d", g);
        System.out.printf("%-10.2f\n", getAverage());
    }
}

public class StudentGradeTracker {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Student Grade Tracker (Professional Portfolio Version) ===");

        // Step 1: Enter subjects
        System.out.print("Enter number of subjects: ");
        int subjectCount = sc.nextInt();
        sc.nextLine();
        String[] subjects = new String[subjectCount];
        for (int i = 0; i < subjectCount; i++) {
            System.out.print("Enter subject " + (i + 1) + ": ");
            subjects[i] = sc.nextLine();
        }

        // Step 2: Enter students
        ArrayList<Student> students = new ArrayList<>();
        String more = "yes";
        while (more.equalsIgnoreCase("yes")) {
            System.out.print("\nEnter student name: ");
            String name = sc.nextLine();

            int[] grades = new int[subjectCount];
            for (int i = 0; i < subjectCount; i++) {
                System.out.print("Enter " + subjects[i] + " grade: ");
                grades[i] = sc.nextInt();
            }
            sc.nextLine();

            students.add(new Student(name, subjects, grades));

            System.out.print("Do you want to add another student? (yes/no): ");
            more = sc.nextLine();
        }

        // Step 3: Sort students by average (highest first)
        students.sort((s1, s2) -> Double.compare(s2.getAverage(), s1.getAverage()));

        // Step 4: Display summary
        StringBuilder report = new StringBuilder();
        report.append("\n=== Summary Report ===\n\n");

        // Header
        String header = String.format("%-15s", "Name");
        for (String sub : subjects)
            header += String.format("%-10s", sub);
        header += String.format("%-10s\n", "Average");
        report.append(header);
        report.append("----------------------------------------------------\n");

        // Students
        for (Student s : students) {
            String row = String.format("%-15s", s.name);
            for (int g : s.grades)
                row += String.format("%-10d", g);
            row += String.format("%-10.2f\n", s.getAverage());
            report.append(row);
        }

        // Subject stats
        double classTotal = 0;
        int totalGrades = 0;
        for (int i = 0; i < subjects.length; i++) {
            int highest = students.get(0).grades[i];
            int lowest = students.get(0).grades[i];

            for (Student s : students) {
                int g = s.grades[i];
                if (g > highest)
                    highest = g;
                if (g < lowest)
                    lowest = g;
                classTotal += g;
                totalGrades++;
            }
            report.append(String.format("\nSubject: %s | Highest: %d | Lowest: %d\n", subjects[i], highest, lowest));
        }

        double classAverage = classTotal / totalGrades;
        report.append(String.format("\nOverall Class Average: %.2f\n", classAverage));

        // Display report in console
        System.out.println(report);

        // Step 5: Search for a student
        System.out.print("\nDo you want to search a student by name? (yes/no): ");
        String searchOption = sc.nextLine();
        if (searchOption.equalsIgnoreCase("yes")) {
            System.out.print("Enter student name to search: ");
            String searchName = sc.nextLine();
            boolean found = false;
            for (Student s : students) {
                if (s.name.equalsIgnoreCase(searchName)) {
                    System.out.println("\nStudent Found:");
                    s.display();
                    found = true;
                    break;
                }
            }
            if (!found)
                System.out.println("Student not found.");
        }

        // Step 6: Save report to text file
        try (FileWriter fw = new FileWriter("StudentReport.txt")) {
            fw.write(report.toString());
            System.out.println("\nReport saved successfully to StudentReport.txt");
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }

        sc.close();
    }
}
