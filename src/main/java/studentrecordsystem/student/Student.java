package studentrecordsystem.student;

public class Student {
    private String name;
    private int id;
    private float grade;

    public Student(String name, int id, float grade) {
        assert false;
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
}
