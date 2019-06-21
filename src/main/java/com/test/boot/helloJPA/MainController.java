package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/person")
public class MainController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addPerson(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam Integer age
    ) {
        Person p = new Person();
        p.setFirstName(firstName);
        p.setLastName(lastName);
        p.setEmail(email);
        p.setAge(age);
        personRepository.save(p);
        return "OK";
    }

}
