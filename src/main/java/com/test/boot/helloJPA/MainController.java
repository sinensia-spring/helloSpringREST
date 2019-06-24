package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(path="/persons")
public class MainController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public Iterable<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @PostMapping
    public Person createPerson(
            HttpServletResponse response,
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
        response.setStatus(201);
        response.setHeader("Location", "/person/"+p.getId());
        return p;
    }

    @GetMapping(path="/{id}")
    public Person retrievePerson(
            HttpServletResponse response,
                @PathVariable(name="id") Integer id
    ) throws IOException {
        Optional found = personRepository.findById(id);
        if(found.isPresent()) {
            Person p = (Person) found.get();
            return p;
        }
        response.sendError(404,"Not found");
        return null;
    }

    @PutMapping(path="/{id}")
    public Person updatePerson(
            HttpServletResponse response,
            @PathVariable(name="id") Integer id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam Integer age
    ) throws IOException {
        Optional found = personRepository.findById(id);
        if(found.isPresent()) {
            Person p = (Person)found.get();
            p.setFirstName(firstName);
            p.setLastName(lastName);
            p.setEmail(email);
            p.setAge(age);
            personRepository.save(p);
            return p;
        }
        response.sendError(404, "Not found");
        return null;
    }

    @DeleteMapping(path = "/{id}")
    public Object deletePerson(
            HttpServletResponse response,
            @PathVariable(name="id") Integer id
    ) throws IOException {
        Optional found = personRepository.findById(id);
        if(found.isPresent()) {
            personRepository.deleteById(id);
            response.setStatus(204);
            return null;
        } else {
            response.sendError(404, "Not found");
            return null;
        }
    }

}
