package studentrecordsystem.recordsystem;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import studentrecordsystem.student.Student;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RecordSystemTest {
    private RecordSystem sys;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    static void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }
    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
    }
    @BeforeEach
    void setUpRecordSystem() {
        HashMap<Integer, Student> existingStudents = new HashMap<>();
        existingStudents.put(10154707, new Student("Jason", 10154707, 82));
        existingStudents.put(10163270, new Student("Sanchayata", 10163270, 70));
        existingStudents.put(10199398, new Student("John", 10199398, 57));
        sys = new RecordSystem(existingStudents);
    }


    @Test
    void testGetStudents() {
        HashMap<Integer, Student> expected = new HashMap<>();
        expected.put(10154707, new Student("Jason", 10154707, 82));
        expected.put(10163270, new Student("Sanchayata", 10163270, 70));
        expected.put(10199398, new Student("John", 10199398, 57));

//        assertTrue(sys.getStudents().equals(expected));
        assertEquals(sys.getStudents(), expected);
    }


    @ParameterizedTest
    @CsvSource({
            "Henry, 23054570, 69",
            "Sanchayata, 33136700, 32.2",
            "Sarah, 88888888, 0.9"
    })
    void testAddStudent(String name, int id, float grade) {
        Student student = new Student(name, id, grade);
        sys.add(student);

        assertTrue(sys.getStudents().containsKey(id));
        assertEquals(sys.getStudents().get(id).getName(), name);
        assertEquals(sys.getStudents().get(id).getId(), id);
        assertEquals(sys.getStudents().get(id).getGrade(), grade);
    }
    @ParameterizedTest
    @CsvSource({
            "Jason, 10154707, 82",
            "Sanchayata, 10163270, 70",
            "John, 10199398, 57"
    })
    void testAddExistingStudent(String name, int id, float grade) {
        Student duplicateStudent = new Student(name, id, grade);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.add(duplicateStudent));
        assertEquals("Student already exists in the system", e.getMessage());
    }


    @ParameterizedTest
    @CsvSource({
            "Jason, 10154707, 82",
            "Sanchayata, 10163270, 70",
            "John, 10199398, 57"
    })
    void testDeleteStudent(String name, int id, float grade) {
        Student student = new Student(name, id, grade);
        sys.delete(student);
        assertFalse(sys.getStudents().containsKey(id));
    }
    @ParameterizedTest
    @CsvSource({
            "Henry, 23054570, 69",
            "Sanchayata, 33136700, 32.2",
            "Sarah, 88888888, 0.9"
    })
    void testDeleteNonExistingStudent(String name, int id, float grade) {
        Student student = new Student(name, id, grade);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.delete(student));
        assertEquals("Student does not exist in the system", e.getMessage());
    }


    @ParameterizedTest
    @CsvSource({
            "10154707", "10163270", "10199398"
    })
    void testDeleteStudentWithID(int id) {
        sys.delete(id);
        assertFalse(sys.getStudents().containsKey(id));
    }
    @ParameterizedTest
    @CsvSource({
            "9823011", "1", "967"
    })
    void testDeleteNonExistingStudentWithId(int id) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.delete(id));
        assertEquals(String.format("Student with id %d does not exist in the system", id), e.getMessage());
    }
    @ParameterizedTest
    @CsvSource({
            "-1", "0", "-100000"
    })
    void testDeleteStudentWithInvalidId(int invalidId) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.delete(invalidId));
        assertEquals(String.format("Invalid ID %d. ID must be a positive integer", invalidId), e.getMessage());
    }


    @ParameterizedTest
    @CsvSource({
            "10154707, Jase",
            "10163270, Sancha",
            "10199398, Jonathan"
    })
    void testUpdateStudentName(int id, String newName) {
        sys.updateName(id, newName);
        assertEquals(sys.getStudents().get(id).getName(), newName);
    }
    @ParameterizedTest
    @CsvSource({
            "-1, Jason", "0, Tim", "-100000, Jessica"
    })
    void testUpdateStudentNameWithInvalidId(int invalidId, String newName) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.updateName(invalidId, newName));
        assertEquals(String.format("Invalid ID %d. ID must be a positive integer", invalidId), e.getMessage());
    }
    @ParameterizedTest
    @CsvSource({
            "10154707, Takeshi69",
            "10163270, Ke$ha",
            "10199398, tosh.o",
            "10199398, !\"£$%^&*()"
    })
    void testUpdateStudentNameWithInvalidNewName(int id, String invalidNewName) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.updateName(id, invalidNewName));
        assertEquals(
                String.format("Invalid new name %s. New name must contain alphabetic characters and spaces only",
                        invalidNewName),
                e.getMessage()
        );
    }


    @ParameterizedTest
    @CsvSource({
            "10154707, 101", "10163270, 2", "10199398, 10100998"
    })
    void testUpdateStudentId(int id, int newId) {
        Student prevStudentRecord = sys.getStudents().get(id);
        String name = prevStudentRecord.getName();
        float grade = prevStudentRecord.getGrade();
        sys.updateId(id, newId);

        assertTrue(sys.getStudents().containsKey(newId));
        assertFalse(sys.getStudents().containsKey((id)));

        Student newStudentRecord = sys.getStudents().get(newId);
        assertEquals(newStudentRecord.getId(), newId);
        assertEquals(newStudentRecord.getName(), name);
        assertEquals(newStudentRecord.getGrade(), grade);
    }
    @ParameterizedTest
    @CsvSource({
            "-1, 101", "0, 2", "-100000, 10100998"
    })
    void testUpdateStudentIdWithInvalidId(int invalidId, int newId) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.updateId(invalidId, newId));
        assertEquals(String.format("Invalid ID %d. ID must be a positive integer", invalidId), e.getMessage());
    }
    @ParameterizedTest
    @CsvSource({
            "10154707, -1", "10163270, 0", "10199398, -100000"
    })
    void testUpdateStudentIdWithInvalidNewId(int id, int invalidNewId) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.updateId(id, invalidNewId));
        assertEquals(
                String.format("Invalid new ID %d. New ID must be a positive integer", invalidNewId),
                e.getMessage()
        );
    }
    @ParameterizedTest
    @CsvSource({
            "10163270, 10154707"
    })
    void testUpdateStudentIdWithExistingStudentId(int id, int existingStudentId) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.updateId(id, existingStudentId));
        assertEquals(
                String.format("New ID: %d already exists in the system", existingStudentId),
                e.getMessage()
        );
    }


    @ParameterizedTest
    @CsvSource({
            "10154707, 80.8", "10163270, 77.2", "10199398, 12.223"
    })
    void testUpdateStudentGrade(int id, int newGrade) {
        sys.updateGrade(id, newGrade);
        assertEquals(sys.getStudents().get(id).getGrade(), newGrade);
    }
    @ParameterizedTest
    @CsvSource({
            "-1, 80.8", "0, 57.2", "-100000, 12.223"
    })
    void testUpdateStudentGradeWithInvalidId(int invalidId, float newGrade) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.updateGrade(invalidId, newGrade));
        assertEquals(String.format("Invalid ID %d. ID must be a positive integer", invalidId), e.getMessage());
    }
    @ParameterizedTest
    @CsvSource({
            "10154707, 101",
            "10163270, -1",
            "10199398, -123590",
            "10199398, 65784932"
    })
    void testUpdateStudentGradeWithInvalidGrade(int id, float invalidGrade) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> sys.updateGrade(id, invalidGrade));
        assertEquals(
                String.format("Invalid new grade %f. New grade must be between 0 and 100 (inclusive)",
                              invalidGrade),
                e.getMessage()
        );
    }


    @Test
    void testViewAllStudents() {
        String expectedDisplay = """
                 ____________________________________________________________________________________________
                |                                   Student Record System                                    |
                |--------------------------------------------------------------------------------------------|
                |ID             |Name                                                                  |Grade|
                |--------------------------------------------------------------------------------------------|
                |10154707       |Jason                                                                 |82.00|
                |10163270       |Sanchayata                                                            |70.00|
                |10199398       |John                                                                  |57.00|
                 --------------------------------------------------------------------------------------------
                """;
        sys.viewAll();
        assertEquals(expectedDisplay, outContent.toString());
    }
    @Test
    void testViewAllStudentsWhenSystemIsEmpty() {
        RecordSystem rSys = new RecordSystem();
        rSys.viewAll();
        assertEquals("Student Record System is empty...", outContent.toString());
    }


    @Test
    void testSave() {
        // TODO: Implement this test
        assert false;
    }
    @Test
    void testLoad() {
        // TODO: Implement this test
        assert false;
    }
}
