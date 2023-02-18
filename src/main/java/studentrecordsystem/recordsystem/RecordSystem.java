package studentrecordsystem.recordsystem;

import studentrecordsystem.student.Student;

import java.util.HashMap;

public class RecordSystem {
    private HashMap<Integer, Student> students;

    public RecordSystem() {
        assert false;
    }
    public RecordSystem(HashMap<Integer, Student> existingStudents) {
        assert false;
    }

    public boolean add(Student student) {
        return false;
    }

    public boolean delete(int id) {
        return false;
    }
    public boolean delete(Student student) {
        return false;
    }

    public HashMap<Integer, Student> getStudents() { return students; }


    public boolean updateName(int id, String newName) {
        return false;
    }
    public boolean updateId(int id, int newId) {
        return false;
    }
    public boolean updateGrade(int id, float newGrade) {
        return false;
    }

    public void viewAll() {
        assert false;
    }
}
