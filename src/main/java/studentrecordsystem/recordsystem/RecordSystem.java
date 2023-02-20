package studentrecordsystem.recordsystem;

import org.jetbrains.annotations.NotNull;
import studentrecordsystem.student.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RecordSystem {
    private final HashMap<Integer, Student> students;
    private final static float minGrade = 0;
    private final static float maxGrade = 100;
    private final static int startId = 1;
    private final static String nameFormat = "[a-zA-Z][a-zA-Z ]+[a-zA-Z]";
    private final static String fileNameFormat = "[-_a-zA-Z0-9]+";
    private final static String header = """
             ____________________________________________________________________________________________
            |                                   Student Record System                                    |
            """;
    private final static String separator = """
            |--------------------------------------------------------------------------------------------|
            """;
    private final static String columnHeadings = """
            |ID             |Name                                                                  |Grade|
            """;
    private final static String footer = """
             --------------------------------------------------------------------------------------------
            """;

    public RecordSystem() {
        students = new HashMap<>();
    }
    public RecordSystem(@NotNull HashMap<Integer, Student> existingStudents) {
        students = new HashMap<>();
        for (var entry : existingStudents.entrySet()) {
            Student studentCopy = new Student(
                    entry.getValue().getName(),
                    entry.getValue().getId(),
                    entry.getValue().getGrade()
            );
            students.put(entry.getKey(), studentCopy);
        }
    }

    public void add(@NotNull Student student) throws IllegalArgumentException {
        String name = student.getName();
        int id = student.getId();
        float grade = student.getGrade();

        if (id < 1) {
            throw new IllegalArgumentException(String.format("Invalid ID %d. ID must be a positive integer", id));
        }
        if (!Pattern.matches(nameFormat, name)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Invalid new name %s. New name must contain alphabetic characters and spaces only",
                            name
                    )
            );
        }
        if (grade < minGrade || grade > maxGrade) {
            throw new IllegalArgumentException(
                    String.format("Invalid new grade %f. New grade must be between 0 and 100 (inclusive)", grade)
            );
        }
        if (students.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student already exists in the system");
        }

        Student studentCopy = new Student(name, id, grade);
        students.put(student.getId(), studentCopy);
    }

    public void delete(int id) throws IllegalArgumentException {
        if (id < startId) {
            throw new IllegalArgumentException(
                    String.format("Invalid ID %d. ID must be a positive integer", id)
            );
        }
        if (!students.containsKey(id)) {
            throw new IllegalArgumentException(
                    String.format("Student with id %d does not exist in the system", id)
            );
        }
        students.remove(id);
    }
    public void delete(@NotNull Student student) throws IllegalArgumentException {
        int id = student.getId();
        if (id < 1) {
            throw new IllegalArgumentException(
                    String.format("Invalid ID %d. ID must be a positive integer", id)
            );
        }
        if (!students.containsKey(id)) {
            throw new IllegalArgumentException("Student does not exist in the system");
        }
        students.remove(id, student);
    }

    public HashMap<Integer, Student> getStudents() { return students; }


    public void updateName(int id, String newName) throws IllegalArgumentException {
        if (id < 1) {
            throw new IllegalArgumentException(
                    String.format("Invalid ID %d. ID must be a positive integer", id)
            );
        }
        if (!students.containsKey(id)) {
            throw new IllegalArgumentException(
                    String.format("Student with id %d does not exist in the system", id)
            );
        }
        if (!Pattern.matches(nameFormat, newName)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Invalid new name %s. New name must contain alphabetic characters and spaces only",
                            newName
                    )
            );
        }
        students.get(id).setName(newName);
    }
    public void updateId(int id, int newId) throws IllegalArgumentException {
        if (id < 1) {
            throw new IllegalArgumentException(
                    String.format("Invalid ID %d. ID must be a positive integer", id)
            );
        }
        if (newId < 1) {
            throw new IllegalArgumentException(
                    String.format("Invalid new ID %d. New ID must be a positive integer", newId)
            );
        }
        if (!students.containsKey(id)) {
            throw new IllegalArgumentException(
                    String.format("Student with id %d does not exist in the system", id)
            );
        }
        if (students.containsKey(newId)) {
            throw new IllegalArgumentException(
                    String.format("New ID: %d already exists in the system", newId)
            );
        }
        Student studentCopy = students.get(id);
        studentCopy.setId(newId);
        students.put(newId, studentCopy);
        students.remove(id);
    }
    public void updateGrade(int id, float newGrade) throws IllegalArgumentException {
        if (id < 1) {
            throw new IllegalArgumentException(
                    String.format("Invalid ID %d. ID must be a positive integer", id)
            );
        }
        if (newGrade < minGrade || newGrade > maxGrade) {
            throw new IllegalArgumentException(
                    String.format(
                            "Invalid new grade %f. New grade must be between 0 and 100 (inclusive)",
                            newGrade
                    )
            );
        }
        students.get(id).setGrade(newGrade);
    }

    public void viewAll() {
        if (students.size() == 0) {
            System.out.print("Student Record System is empty...\n");
        } else {
            System.out.print(header);
            System.out.print(separator);
            System.out.print(columnHeadings);
            System.out.print(separator);

            for (Student student : students.values()) {
                System.out.print("|");
                System.out.printf(
                        "%1$-15d|%2$-70s|%3$.2f|\n",
                        student.getId(),
                        student.getName(),
                        student.getGrade()
                );
            }

            System.out.print(footer);
        }
    }

    public void save(String fileName) throws IOException, IllegalArgumentException {
        if (students.size() == 0) {
            throw new IOException("Student File System is empty");
        }
        if (!Pattern.matches(fileNameFormat, fileName)) {
            throw new IllegalArgumentException(String.format("Invalid file name %s", fileName));
        }
        FileWriter fWriter = new FileWriter(fileName + ".txt");
        for (Student student : students.values()) {
            fWriter.write(String.format("%1$d,%2$s,%3$f\n", student.getId(), student.getName(), student.getGrade()));
        }
        fWriter.close();
    }
//    public void load(String fileName) throws IOException {
//        assert false;
//    }
}
