import java.util.ArrayList;
import java.util.Objects;

public class Service {

    public static void save(String firstName, String lastName) {
        DbRepository db = DbRepository.getInstance();
        db.save(new Student(firstName, lastName));
    }

    public static void save(String firstName, String lastName, int averageGrade) {
        DbRepository db = DbRepository.getInstance();
        db.save(new Student(firstName, lastName, averageGrade));
    }

    public static ArrayList<Student> findAll() {
        DbRepository db = DbRepository.getInstance();
        return db.findAll();
    }

    public static Student findById(long id) {
        DbRepository db = DbRepository.getInstance();
        Student student = db.findById(id);
        if (Objects.isNull(student.getFirstName())) {
            return null;
        }
        return student;
    }

    public static Student delete(long id) {
        DbRepository db = DbRepository.getInstance();
        Student student = db.delete(id);
        if (Objects.isNull(student.getFirstName())) {
            return null;
        }
        return student;
    }

    public static int update(long id, String firstName, String lastName, int averageGrade) {
        DbRepository db = DbRepository.getInstance();
        Student student = new Student(id, firstName, lastName, averageGrade);
        return db.update(student);
    }

    public static int update(long id, int averageGrade) {
        DbRepository db = DbRepository.getInstance();
        Student student = new Student(id, null, null, averageGrade);
        return db.update(student);
    }
}
