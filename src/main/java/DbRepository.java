import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class DbRepository {
    private static final DbRepository INSTANCE = new DbRepository();
    private Connection connection;
    private final String URL = "jdbc:h2:mem:test";
    private final String CREATE_TABLE = "Create table student (id IDENTITY NOT NULL PRIMARY KEY, firstName varchar(50), lastName varchar(50), averageGrade int)";
    private final String INSERT_QUERY = "Insert into student (firstName, lastName, averageGrade) values (?, ?, ?)";
    private final String UPDATE_QUERY = "UPDATE student SET firstName=?, lastName=?, averageGrade=? WHERE id=?";
    private final String SELECT_ID_QUERY = "SELECT * FROM student WHERE id=?";
    private final String DELETE_QUERY = "DELETE FROM student WHERE id=?";
    private final String SELECT_ALL_QUERY = "SELECT * FROM student";

    private DbRepository() {
        this.connect();
    }

    public static DbRepository getInstance() {
        return INSTANCE;
    }

    public void save(Student student) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getAverageGrade());
            int result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка SQL: " + e.getMessage());
        }
    }

    public Student findById(long id) {
        Student student = new Student();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student.setId(resultSet.getLong("id"));
                student.setFirstName(resultSet.getString("firstName"));
                student.setLastName(resultSet.getString("lastName"));
                student.setAverageGrade(resultSet.getInt("averageGrade"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL: " + e.getMessage());
        }
        return student;
    }

    public ArrayList<Student> findAll() {
        ArrayList<Student> resultList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resultList.add(new Student(resultSet.getLong("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("LastName"),
                        resultSet.getInt("averageGrade")));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка SQL: " + e.getMessage());
        }
        return resultList;
    }

    public Student delete(long id) {
        Student student = this.findById(id);
        if (!Objects.isNull(student.getFirstName())) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Ошибка SQL: " + e.getMessage());
            }
        }
        return student;
    }

    public int update(Student student) {
        Student oldStudent = this.findById(student.getId());
        int result = 0;
        if (oldStudent.getId() == student.getId()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
                preparedStatement.setString(1, student.getFirstName() == null ? oldStudent.getFirstName() : student.getFirstName());
                preparedStatement.setString(2, student.getLastName() == null ? oldStudent.getLastName() : student.getLastName());
                preparedStatement.setInt(3, student.getAverageGrade() > 0 ? student.getAverageGrade() : oldStudent.getAverageGrade());
                preparedStatement.setLong(4, student.getId());
                result = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Ошибка SQL: " + e.getMessage());
            }
        }
        return result;
    }

    public void connect() {
        if (Objects.isNull(connection)) {
            try {
                connection = DriverManager.getConnection(URL);
                Statement statement = connection.createStatement();
                statement.executeUpdate(CREATE_TABLE);
                statement.close();
            } catch (SQLException e) {
                System.out.println("Ошибка SQL: " + e.getMessage());
            }
        }
    }

    public void close() {
        if (!Objects.isNull(connection)) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.out.println("Ошибка SQL: " + e.getMessage());
            }
        }
    }
}
