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
    public CustomResponse getAllPersons() {
        return new CustomResponse(
                true,
                null,
                personRepository.findAll()
        );
    }

    @PostMapping
    public CustomResponse createPerson(
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
        return new CustomResponse(true, null, p);
    }

    @GetMapping(path="/{id}")
    public CustomResponse retrievePerson(
            HttpServletResponse response,
                @PathVariable(name="id") Integer id
    ) throws IOException {
        Optional found = personRepository.findById(id);
        if(found.isPresent()) {
            Person p = (Person) found.get();
            return new CustomResponse(true, null, p);
        }
        response.setStatus(404);
        return new CustomResponse(false, "Not found");
    }

    @PutMapping(path="/{id}")
    public CustomResponse updatePerson(
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
            return new CustomResponse(true, null, p);
        }
        response.setStatus(404);
        return new CustomResponse(false, "Not found");
    }

    @DeleteMapping(path = "/{id}")
    public CustomResponse deletePerson(
            HttpServletResponse response,
            @PathVariable(name="id") Integer id
    ) throws IOException {
        Optional found = personRepository.findById(id);
        if(found.isPresent()) {
            personRepository.deleteById(id);
            response.setStatus(204);
            return new CustomResponse(true, null, null);
        } else {
            response.setStatus(404);
            return new CustomResponse(false, "Not found");
        }
    }

}
