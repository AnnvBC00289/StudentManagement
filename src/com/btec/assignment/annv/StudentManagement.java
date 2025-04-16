package com.btec.assignment.annv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

// Student class (unchanged from P4)
class Student {
    private String id;
    private String name;
    private double marks;

    public Student(String id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getMarks() { return marks; }
    public void setName(String name) { this.name = name; }
    public void setMarks(double marks) { this.marks = marks; }

    public String getRanking() {
        if (marks < 5.0) return "Fail";
        else if (marks < 6.5) return "Medium";
        else if (marks < 7.5) return "Good";
        else if (marks <= 9.0) return "Very Good";
        else return "Excellent";
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Marks: " + marks + ", Rank: " + getRanking();
    }
}

// StudentManager class with enhanced error handling
class StudentManager {
    private ArrayList<Student> students;
    private HashMap<String, Student> studentMap;

    public StudentManager() {
        students = new ArrayList<>();
        studentMap = new HashMap<>();
    }

    // Add student with validation
    public void addStudent(String id, String name, double marks) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("Student ID cannot be empty!");
        }
        if (studentMap.containsKey(id)) {
            throw new Exception("Student ID " + id + " already exists!");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("Student name cannot be empty!");
        }
        if (marks < 0 || marks > 10) {
            throw new Exception("Marks must be between 0 and 10!");
        }
        Student student = new Student(id, name, marks);
        students.add(student);
        studentMap.put(id, student);
        System.out.println("Student added successfully: " + student);
    }

    // Sort students by marks
    public void sortStudentsByMarks() {
        Collections.sort(students, Comparator.comparingDouble(Student::getMarks).reversed());
        System.out.println("Students sorted by marks:");
        displayStudents();
    }

    // Display students
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in the list.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
}

// Main class to test the provided data
public class StudentManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        // Preload the provided students to simulate the scenario
        try {
            manager.addStudent("S02", "Nguyen Thanh Chang", 9.5);
            manager.addStudent("S01", "Nguyen Vi An", 9.0);
            manager.addStudent("S04", "Quach Van Phi Hung", 9.0);
            manager.addStudent("S03", "Nguyen Tan Luc", 8.0);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Sort Students by Marks");
            System.out.println("3. Display Students");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks;
                        try {
                            marks = scanner.nextDouble();
                        } catch (InputMismatchException e) {
                            scanner.nextLine();
                            throw new Exception("Invalid marks format! Please enter a number.");
                        }
                        manager.addStudent(id, name, marks);
                        break;
                    case 2:
                        manager.sortStudentsByMarks();
                        break;
                    case 3:
                        manager.displayStudents();
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input! Please enter a valid number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}