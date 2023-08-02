import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String address;
    private String phoneNumber;

    public Student(String name, int rollNumber, String address, String phoneNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Address: " + address + ", Phone Number: " + phoneNumber;
    }
}

class StudentRecord implements Serializable {
    private List<Student> students;

    public StudentRecord() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
    }

    public Student getStudentByRollNumber(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
}

public class StudentRecordManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentRecord studentRecord = loadStudentRecord();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Display Student Information");
            System.out.println("4. Display All Students");
            System.out.println("5. Save Student Record");
            System.out.println("6. Quit");
            System.out.print("Enter your choice (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    addStudent(scanner, studentRecord);
                    break;
                case 2:
                    removeStudent(scanner, studentRecord);
                    break;
                case 3:
                    displayStudentInformation(scanner, studentRecord);
                    break;
                case 4:
                    displayAllStudents(studentRecord);
                    break;
                case 5:
                    saveStudentRecord(studentRecord);
                    break;
                case 6:
                    saveStudentRecord(studentRecord);
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void addStudent(Scanner scanner, StudentRecord studentRecord) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter roll number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        Student newStudent = new Student(name, rollNumber, address, phoneNumber);
        studentRecord.addStudent(newStudent);
        System.out.println("Student added successfully.");
    }

    private static void removeStudent(Scanner scanner, StudentRecord studentRecord) {
        System.out.print("Enter the roll number of the student to remove: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine();

        Student studentToRemove = studentRecord.getStudentByRollNumber(rollNumber);
        if (studentToRemove != null) {
            studentRecord.removeStudent(rollNumber);
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    private static void displayStudentInformation(Scanner scanner, StudentRecord studentRecord) {
        System.out.print("Enter the roll number of the student: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character after reading the integer

        Student student = studentRecord.getStudentByRollNumber(rollNumber);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    private static void displayAllStudents(StudentRecord studentRecord) {
        List<Student> allStudents = studentRecord.getAllStudents();
        if (!allStudents.isEmpty()) {
            System.out.println("All Students:");
            for (Student student : allStudents) {
                System.out.println(student);
            }
        } else {
            System.out.println("No students found.");
        }
    }

    private static StudentRecord loadStudentRecord() {
        try {
            FileInputStream fileInputStream = new FileInputStream("student_record.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            StudentRecord studentRecord = (StudentRecord) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return studentRecord;
        } catch (IOException | ClassNotFoundException e) {
            return new StudentRecord();
        }
    }

    private static void saveStudentRecord(StudentRecord studentRecord) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("student_record.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(studentRecord);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Student record saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save student record.");
        }
    }
}
