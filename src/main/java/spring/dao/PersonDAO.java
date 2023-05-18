package spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spring.models.Person;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person> getPeople(){
        return jdbcTemplate.query("select * from Person", new BeanPropertyRowMapper<>(Person.class));
    }
    //обрабатываем исключение, если нет person с каким-то id
    public Person showByIndex(int id){
       return jdbcTemplate.query("select * from Person where id = ?",new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
               .stream().findAny().orElse(null);
    }

    public void save(Person person){
       jdbcTemplate.update("insert into person values(1,?,?,?)",person.getName(),person.getAge(),person.getEmail());
    }
    public void update(int id,Person updatedPerson){
        jdbcTemplate.update("update person set name = ?, age = ?, email = ? where id = ?",
                updatedPerson.getName(),updatedPerson.getAge(),updatedPerson.getEmail(),id);

    }

    public void delete(int id){
        jdbcTemplate.update("delete from person where id = ?",id);
    }

}
