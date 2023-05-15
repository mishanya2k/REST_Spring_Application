package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.dao.PersonDAO;
import spring.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    //Получим всех людей из DAO и передадим на отображение в представление
    @GetMapping("")
    public String getAllPeople(Model model){
        model.addAttribute("people",personDAO.getPeople());
        return "people/getAllPeople";
    }
    //Получи одного человека по id  из DAO, передадим на отображение в представление
    @GetMapping("/{id}")
    public String showByIndex(@PathVariable("id") int id, Model model){
        model.addAttribute("person",personDAO.showByIndex(id));
        return "people/showByIndex";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") Person person){
        personDAO.save(person);
        return "redirect:/people";
    }
}
