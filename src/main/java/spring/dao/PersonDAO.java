package spring.dao;

import org.springframework.stereotype.Component;
import spring.models.Person;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Class.forName;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private static final String URL = "jdbc:postgresql://localhost:5432/courseDB";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin007";

    private static Connection connection;

    static{
        //хз чо эт
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            //коннект к нашей бд
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getPeople(){
        List<Person> people = new ArrayList<>();

        try {
            //написание запроса
            Statement statement = connection.createStatement();
            String SQL = "select * from person";
            //создание хранение результата для запроса, executeQuery получает данные
            ResultSet resultSet = statement.executeQuery(SQL);
            //наполнение нашего ArrayList значениеями, полученными из запроса
            while(resultSet.next()){
                Person person  = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }
    public Person showByIndex(int id){
        Person person = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from person where id = ?");
                    preparedStatement.setInt(1,id);
                    
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();

                    person = new Person();
                    person.setId(resultSet.getInt("id"));
                    person.setName(resultSet.getString("name"));
                    person.setEmail(resultSet.getString("email"));
                    person.setAge(resultSet.getInt("age"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public void save(Person person){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into person values (1,?,?,?)");
            preparedStatement.setString(1,person.getName());
            preparedStatement.setInt(2,person.getAge());
            preparedStatement.setString(3, person.getEmail());
            //обновляет данные в бд
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(int id,Person updatedPerson){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("Update person set name = ?, age = ?, email = ? where id = ?");
                    preparedStatement.setString(1,updatedPerson.getName());
                    preparedStatement.setInt(2,updatedPerson.getAge());
                    preparedStatement.setString(3, updatedPerson.getEmail());
                    preparedStatement.setInt(4,id);

                    preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int id){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE from person where id = ?");
            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
