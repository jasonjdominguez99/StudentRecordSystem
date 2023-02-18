package studentrecordsystem.student;

import java.util.Objects;

public class Student {
    private String name;
    private int id;
    private float grade;

    public Student(String sName, int sId, float sGrade) {
        name = sName;
        id = sId;
        grade = sGrade;
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public float getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Float.compare(student.grade, grade) == 0 && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
