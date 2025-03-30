import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    @Mock
    private DbRepository db;

    @InjectMocks
    private Service service;

    @Test
    void findByIdTest() {
        long id = 1;
        Student student = new Student(1, "John", "Connor", 80);

        when(db.findById(id)).thenReturn(student);

        Student result = service.findById(id);

        assertFalse(Objects.isNull(result));
        assertEquals(student, result);
    }

    @Test
    void findAllTest() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(1, "John", "Connor", 80));
        students.add(new Student(2, "Mike", "Cherry", 70));

        when(db.findAll()).thenReturn(students);

        ArrayList<Student> result = service.findAll();

        assertFalse(result.isEmpty());
        assertEquals(students, result);
    }

    @Test
    void saveTest1() {
        Student student = new Student("John", "Connor", 80);
        String firstName = "John";
        String lastName = "Connor";
        int averageGrade = 80;

        doNothing().when(db).save(student);

        service.save(firstName, lastName, averageGrade);
    }

    @Test
    void saveTest2() {
        Student student = new Student("John", "Connor");
        String firstName = "John";
        String lastName = "Connor";

        doNothing().when(db).save(student);

        service.save(firstName, lastName);
    }

    @Test
    void deleteTest() {
        long id = 1;
        Student student = new Student(1, "John", "Connor", 80);

        when(db.delete(id)).thenReturn(student);

        Student result = service.delete(id);

        assertEquals(result, student);
    }

    @Test
    void updateTest1() {
        long id = 1;
        String firstName = "John";
        String lastName = "Connor";
        int averageGrade = 80;
        Student student = new Student(id, firstName, lastName, averageGrade);
        int expected = 1;

        when(db.update(student)).thenReturn(expected);

        int result = service.update(id, firstName, lastName, averageGrade);

        assertEquals(expected, result);
    }

    @Test
    void updateTest2() {
        long id = 1;
        int averageGrade = 80;
        Student student = new Student(id, null, null, averageGrade);
        int expected = 1;

        when(db.update(student)).thenReturn(expected);

        int result = service.update(id, averageGrade);

        assertEquals(expected, result);
    }
}
