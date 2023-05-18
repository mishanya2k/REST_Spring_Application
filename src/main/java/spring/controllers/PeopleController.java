package spring.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.dao.PersonDAO;
import spring.models.Person;

import javax.validation.Valid;

@Controller
@Validated
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    //Получим всех людей из DAO и передадим на отображение в представление
    @GetMapping("")
    public String getAllPeople(Model model) {
        model.addAttribute("people", personDAO.getPeople());
        return "people/getAllPeople";
    }

    //Получи одного человека по id  из DAO, передадим на отображение в представление
    @GetMapping("/{id}")
    public String showByIndex(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.showByIndex(id));
        return "people/showByIndex";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.showByIndex(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,@PathVariable("id") int id) {
        if(bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";

    }
}
