package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/persons")
public class MainController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @GetMapping
    public CustomResponse getAllPersons() {
        return new CustomResponse(
                true,
                null,
                personRepository.findAll()
        );
    }

    @GetMapping(path = "/{id}")
    public CustomResponse retrievePerson(
            HttpServletResponse response,
            @PathVariable(name = "id") Integer id
    ) throws IOException {
        Person p = personRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return new CustomResponse(true, null, p);
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
        response.setHeader("Location", "/person/" + p.getId());
        return new CustomResponse(true, null, p);
    }

    @PutMapping(path = "/{id}")
    public CustomResponse updatePerson(
            HttpServletResponse response,
            @PathVariable(name = "id") Integer id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam Integer age
    ) throws IOException {
        Optional found = personRepository.findById(id);
        if (found.isPresent()) {
            Person p = (Person) found.get();
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
            @PathVariable(name = "id") Integer id
    ) throws IOException {
        Optional found = personRepository.findById(id);
        if (found.isPresent()) {
            personRepository.deleteById(id);
            response.setStatus(204);
            return new CustomResponse(true, null, null);
        } else {
            response.setStatus(404);
            return new CustomResponse(false, "Not found");
        }
    }

    @GetMapping(path = "/{id}/phones")
    public CustomResponse retrievePhones(
            @PathVariable Integer id
    ) {
        Person pp = new Person(id);
        return new CustomResponse(
                true,
                null,
                // personRepository.findById(id).get().getPhones()
                phoneRepository.findByPerson(pp)
        );
    }

    @PostMapping(path = "/{id}/phones")
    public CustomResponse createPhone(
            @PathVariable Integer id,
            @RequestParam String type,
            @RequestParam String number
    ) {
        Person pp = personRepository.findById(id).get();
        Phone phone = new Phone();
        phone.setType(type);
        phone.setNumber(number);
        phone.setPerson(pp);
        phoneRepository.save(phone);
        return new CustomResponse(true, null, phone);
    }

    @GetMapping(path="/{person_id}/phones/{phone_id}")
    public CustomResponse retrievePhone(
        HttpServletResponse response,
        @PathVariable Integer person_id,
        @PathVariable Integer phone_id
    ) {

        Optional found =  phoneRepository.findByPersonAndId(
                new Person(person_id),
                phone_id
        );
        response.setStatus(found.isPresent() ? 200 : 404);
        return new CustomResponse(
                found.isPresent(),
                found.isPresent() ? null : "Not found",
                found.isPresent() ? found.get() : null
        );
    }

    @PutMapping(path="/{person_id}/phones/{phone_id}")
    public CustomResponse updatePhone(
            HttpServletResponse response,
            @PathVariable Integer person_id,
            @PathVariable Integer phone_id,
            @RequestParam String type,
            @RequestParam String number
    ) {
        // TODO: Fill in the blanks
        return null;
    }

    @DeleteMapping(path="/{person_id}/phones/{phone_id}")
    public CustomResponse deletePhone(
            HttpServletResponse response,
            @PathVariable Integer person_id,
            @PathVariable Integer phone_id
    ) {
        // TODO: Fill in the blanks
        return null;
    }


}
