import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DbRepositoryTest {
    DbRepository db = DbRepository.getInstance();
    final Student student1 = new Student(1, "John", "Connor", 80);
    final Student student2 = new Student(2, "Mike", "Cherry", 70);
    final Student student3 = new Student(3, "Niko", "Bellyache", 60);
    final long id1 = 1;
    final long id2 = 2;
    final long id3 = 3;
    final int one = 1;

    @BeforeEach
    void init() {
        db.connect();
        db.save(student1);
        db.save(student2);
    }

    @AfterEach
    void after() {
        db.close();
    }

    @Test
    void saveTest() {
        db.save(student3);

        assertEquals(student3, db.findById(id3));
    }

    @Test
    void findByIdTest() {
        assertEquals(student1, db.findById(id1));
    }

    @Test
    void findAllTest() {
        ArrayList<Student> students = db.findAll();

        assertEquals(student1, students.get(0));
        assertEquals(student2, students.get(1));
    }

    @Test
    void deleteTest() {
        Student result = db.delete(id2);

        ArrayList<Student> students = db.findAll();

        assertEquals(student2, result);
        assertEquals(student1, students.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> students.get(1));
    }

    @Test
    void updateTest() {
        Student newStudent = new Student(2,"Necto", "Drygoi", 100);

        int result = db.update(newStudent);

        assertEquals(one, result);
        assertEquals(newStudent, db.findById(id2));
    }
}
