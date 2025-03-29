import java.util.ArrayList;
import java.util.Objects;

public class Service {
    private final DbRepository db;

    public Service(DbRepository db) {
        this.db = db;
    }

    public void save(String firstName, String lastName) {
        db.save(new Student(firstName, lastName));
    }

    public void save(String firstName, String lastName, int averageGrade) {
        db.save(new Student(firstName, lastName, averageGrade));
    }

    public ArrayList<Student> findAll() {
        return db.findAll();
    }

    public Student findById(long id) {
        Student student = db.findById(id);
        if (Objects.isNull(student.getFirstName())) {
            return null;
        }
        return student;
    }

    public Student delete(long id) {
        Student student = db.delete(id);
        if (Objects.isNull(student.getFirstName())) {
            return null;
        }
        return student;
    }

    public int update(long id, String firstName, String lastName, int averageGrade) {
        Student student = new Student(id, firstName, lastName, averageGrade);
        return db.update(student);
    }

    public int update(long id, int averageGrade) {
        Student student = new Student(id, null, null, averageGrade);
        return db.update(student);
    }
}
