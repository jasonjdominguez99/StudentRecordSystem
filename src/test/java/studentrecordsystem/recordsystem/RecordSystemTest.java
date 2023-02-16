package studentrecordsystem.recordsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import studentrecordsystem.student.Student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecordSystemTest {
    private RecordSystem sys;

    @BeforeEach
    void setUp() {
        sys = new RecordSystem();
    }
    @ParameterizedTest
    @CsvSource({
            "Jason, 10154707, 82",
            "Sanchayata, 10163270, 70"
    })
    void testAddValidStudent(String name, int id, float grade) {
        Student student = new Student(name, id, grade);
        sys.add(student);
        assertTrue(sys.has(student));
    }

    @Test
    void testAddExistingStudent() {
        Student student = new Student("John", 10199398, 80);
        sys.add(student);
        Student duplicateStudent = new Student("John", 10199398, 80);
        assertFalse(sys.add(student));
    }

    @Test
    void testDeleteExistingStudent() {
        Student student = new Student("John", 10199398, 80);
        sys.add(student);
        sys.delete(student);
        assertFalse(sys.has(student));
    }

    @Test
    void testDeleteNonExistingStudent() {
        Student student = new Student("John", 10199398, 80);
        assertFalse(sys.delete(student));
    }

    @Test
    void testUpdateStudentName() {
        Student student = new Student("John", 10199398, 80);
        sys.add(student);
        String newName = "Jason";
        assertTrue(sys.updateName(student, newName));
    }
}
