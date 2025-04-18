package com.btec.assignment.annv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

// Lớp Student để lưu trữ thông tin sinh viên
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

    // Xác định xếp hạng dựa trên điểm
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

// Lớp StudentManager để quản lý danh sách sinh viên
class StudentManager {
    private ArrayList<Student> students;
    private HashMap<String, Student> studentMap;

    public StudentManager() {
        students = new ArrayList<>();
        studentMap = new HashMap<>();
    }

    // Thêm sinh viên
    public void addStudent(String id, String name, double marks) throws Exception {
        if (studentMap.containsKey(id)) {
            throw new Exception("Student ID " + id + " already exists!");
        }
        if (marks < 0 || marks > 10) {
            throw new Exception("Marks must be between 0 and 10!");
        }
        Student student = new Student(id, name, marks);
        students.add(student);
        studentMap.put(id, student);
        System.out.println("Student added successfully: " + student);
    }

    // Sửa thông tin sinh viên
    public void editStudent(String id, String newName, double newMarks) throws Exception {
        Student student = studentMap.get(id);
        if (student == null) {
            throw new Exception("Student ID " + id + " not found!");
        }
        if (newMarks < 0 || newMarks > 10) {
            throw new Exception("Marks must be between 0 and 10!");
        }
        student.setName(newName);
        student.setMarks(newMarks);
        System.out.println("Student updated successfully: " + student);
    }

    // Xóa sinh viên
    public void deleteStudent(String id) throws Exception {
        Student student = studentMap.remove(id);
        if (student == null) {
            throw new Exception("Student ID " + id + " not found!");
        }
        students.remove(student);
        System.out.println("Student deleted successfully: " + student);
    }

   // Sắp xếp danh sách sinh viên theo điểm (giảm dần) bằng Quick Sort
public void sortStudentsByMarks() {
    quickSort(students, 0, students.size() - 1);
    System.out.println("Students sorted by marks:");
    displayStudents();
}

// Triển khai Quick Sort
private void quickSort(ArrayList<Student> list, int low, int high) {
    if (low < high) {
        int pi = partition(list, low, high);
        quickSort(list, low, pi - 1);
        quickSort(list, pi + 1, high);
    }
}

// Phân hoạch cho Quick Sort, sắp xếp giảm dần theo điểm, nếu điểm bằng nhau thì theo ID
private int partition(ArrayList<Student> list, int low, int high) {
    double pivotMarks = list.get(high).getMarks();
    String pivotId = list.get(high).getId(); // Tiêu chí phụ khi điểm bằng nhau
    int i = low - 1;
    for (int j = low; j < high; j++) {
        double currentMarks = list.get(j).getMarks();
        String currentId = list.get(j).getId();
        // Sắp xếp giảm dần: điểm lớn hơn hoặc (điểm bằng nhau và ID nhỏ hơn theo chữ cái)
        if (currentMarks > pivotMarks || (currentMarks == pivotMarks && currentId.compareTo(pivotId) <= 0)) {
            i++;
            // Hoán đổi phần tử
            Student temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }
    // Đặt pivot vào đúng vị trí
    Student temp = list.get(i + 1);
    list.set(i + 1, list.get(high));
    list.set(high, temp);
    return i + 1;
}
    // Tìm kiếm nhị phân theo điểm (giả sử danh sách đã sắp xếp)
    public Student binarySearchByMarks(double targetMarks) {
        int left = 0;
        int right = students.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            double midMarks = students.get(mid).getMarks();
            if (midMarks == targetMarks) {
                return students.get(mid);
            } else if (midMarks > targetMarks) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null; // Không tìm thấy
    }

    // Hiển thị danh sách sinh viên
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in the list.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Tìm kiếm sinh viên theo ID bằng HashMap
    public Student findStudentById(String id) {
        return studentMap.get(id);
    }
}

// Chương trình chính
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Delete Student");
            System.out.println("4. Sort Students by Marks");
            System.out.println("5. Search Student by ID");
            System.out.println("6. Search Student by Marks");
            System.out.println("7. Display Students");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Xóa bộ đệm

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks = scanner.nextDouble();
                        manager.addStudent(id, name, marks);
                        break;
                    case 2:
                        System.out.print("Enter ID to edit: ");
                        id = scanner.nextLine();
                        System.out.print("Enter new Name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter new Marks: ");
                        marks = scanner.nextDouble();
                        manager.editStudent(id, name, marks);
                        break;
                    case 3:
                        System.out.print("Enter ID to delete: ");
                        id = scanner.nextLine();
                        manager.deleteStudent(id);
                        break;
                    case 4:
                        manager.sortStudentsByMarks();
                        break;
                    case 5:
                        System.out.print("Enter ID to search: ");
                        id = scanner.nextLine();
                        Student student = manager.findStudentById(id);
                        System.out.println(student != null ? student : "Student not found!");
                        break;
                    case 6:
                        System.out.print("Enter Marks to search: ");
                        marks = scanner.nextDouble();
                        student = manager.binarySearchByMarks(marks);
                        System.out.println(student != null ? student : "No student with marks " + marks);
                        break;
                    case 7:
                        manager.displayStudents();
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}