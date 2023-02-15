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
        boolean addSuccessful;
        addSuccessful = sys.add(student);
        addSuccessful = sys.add(student);
        assertFalse(addSuccessful);
    }
}
