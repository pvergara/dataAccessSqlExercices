package org.ecos.logic;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {

//        System.out.println(getStudents("%b%"));

//        insert(new Student("Rubén","One",5,5));
//
        insertClassrooms("Phoebe");

//        System.out.println(getStudents("%b%"));

        System.out.println(getStudents("%a%",179));

    }

    private static void insert(Student student) throws SQLException, ClassNotFoundException {
        Connection connection = openConnection();

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO school.alumnos (nombre, apellidos, altura, aula) values (?, ?, ?, ?);")){
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastname());
            statement.setInt(3, student.getHeight());
            statement.setInt(4, student.getClassroom());
            statement.executeQuery();

        }
        closeConnection(connection);
    }

    private static void insertClassrooms(String classRoomName) throws SQLException, ClassNotFoundException {
        Connection connection = openConnection();
        connection.setAutoCommit(false);

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO school.asignaturas (NOMBRE) values (?);")){
            statement.setString(1, classRoomName);
            System.out.println("Class " + classRoomName + " added");
            try {
                statement.executeQuery();
            } catch (SQLException e) {
                connection.rollback();
            }finally {
                connection.commit();
            }

        }
        closeConnection(connection);
    }

    private static void delete(int id) throws SQLException, ClassNotFoundException {
        Connection connection = openConnection();

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM school.alumnos WHERE codigo = ?;")){
            statement.setInt(1, id);
            statement.execute();
        }
        closeConnection(connection);
    }

    private static void update(Student student) throws SQLException, ClassNotFoundException {
        Connection connection = openConnection();

        try (PreparedStatement statement = connection.prepareStatement("UPDATE school.alumnos SET nombre=?,apellidos=?,altura=?,aula=? WHERE codigo=?;")){
            statement.setString(1, student.getName());
            statement.setString(2, student.getLastname());
            statement.setInt(3, student.getHeight());
            statement.setInt(4, student.getClassroom());
            statement.setInt(5, student.getId());
            statement.execute();

        }
        closeConnection(connection);
    }
    private static List<Student> getStudents(String partName) throws SQLException, ClassNotFoundException {
        List<Student> studentCollection  = new ArrayList<>();

        Connection connection = openConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT codigo, nombre,apellidos,altura,aula from school.alumnos where nombre LIKE ?")){
            statement.setString(1, partName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                studentCollection.add(
                new Student(
                    resultSet.getInt("codigo"),
                    resultSet.getString("nombre"),
                    resultSet.getString("apellidos"),
                    resultSet.getInt("altura"),
                    resultSet.getInt("aula")
                ));
            }
        }
        closeConnection(connection);
        return studentCollection;
    }

    private static List<Student> getStudents(String partName, int height) throws SQLException, ClassNotFoundException {
        List<Student> studentCollection  = new ArrayList<>();

        Connection connection = openConnection();
        try (PreparedStatement statement = connection.prepareStatement("SELECT codigo, nombre,apellidos,altura,aula from school.alumnos where nombre LIKE ? AND altura > ?")){
            statement.setString(1, partName);
            statement.setInt(2, height);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                studentCollection.add(
                        new Student(
                                resultSet.getInt("codigo"),
                                resultSet.getString("nombre"),
                                resultSet.getString("apellidos"),
                                resultSet.getInt("altura"),
                                resultSet.getInt("aula")
                        ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeConnection(connection);
        return studentCollection;
    }

    private static void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    private static Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");

        return DriverManager.getConnection("jdbc:mariadb://localhost:3306/school","sports_user","sports_user");
    }

}
