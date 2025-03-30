import java.util.Objects;

public class Student {
    private long id;
    private String firstName;
    private String lastName;
    private int averageGrade;

    public Student() {

    }

    public Student(String firstName) {
        this.setFirstName(firstName);
    }

    public Student(String firstName, String lastName) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public Student(String firstName, String lastName, int averageGrade) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAverageGrade(averageGrade);
    }

    public Student(long id, String firstName, String lastName, int averageGrade) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAverageGrade(averageGrade);
    }

    public Student(long id, String firstName) {
        this.setId(id);
        this.setFirstName(firstName);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setAverageGrade(int averageGrade) {
        this.averageGrade = averageGrade;
    }

    public int getAverageGrade() {
        return averageGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && averageGrade == student.averageGrade && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, averageGrade);
    }
}
