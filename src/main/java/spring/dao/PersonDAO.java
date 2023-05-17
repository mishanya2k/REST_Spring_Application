package spring.dao;

import org.springframework.stereotype.Component;
import spring.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT = 0;
    private List<Person> people;
    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT,"Misha"));
        people.add(new Person(++PEOPLE_COUNT,"Oleg"));
        people.add(new Person(++PEOPLE_COUNT,"Igor"));
    }
    public List<Person> getPeople(){
        return people;
    }
    public Person showByIndex(int id){
        return people.stream().filter(person->person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }
    public void update(int id,Person updatedPerson){
        Person personToBeUpdated = showByIndex(id);
        personToBeUpdated.setName(updatedPerson.getName());
    }

    public void delete(int id){
        people.removeIf(p ->p.getId() == id);
    }

}
