package com.test.boot.helloJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
            @Validated @RequestBody Person person
    ) {
        personRepository.save(person);
        response.setStatus(201);
        response.setHeader("Location", "/person/" + person.getId());
        return new CustomResponse(true, null, person);
    }

    @PutMapping(path = "/{id}")
    public CustomResponse updatePerson(
            HttpServletResponse response,
            @PathVariable(name = "id") Integer id,
            @Validated @RequestBody Person person
    ) throws IOException {
        Person p = personRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
            p.setFirstName(person.getFirstName());
            p.setLastName(person.getLastName());
            p.setEmail(person.getEmail());
            p.setAge(person.getAge());
            p.setCompany(person.getCompany());
            personRepository.save(p);
            return new CustomResponse(true, null, p);
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
            @RequestBody Phone phone
    ) {
        Person pp = personRepository.findById(id).get();
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
            @RequestBody Phone p
    ) {
        // TODO: Fill in the blanks
        Person person = personRepository
                .findById(person_id)
                .orElseThrow(() -> new PersonNotFoundException(person_id));
        Phone phone = phoneRepository
                .findByPersonAndId(person, phone_id)
                .orElseThrow(()->new PhoneNotFoundException(phone_id));
        phone.setType(p.getType());
        phone.setNumber(p.getNumber());
        phoneRepository.save(phone);
        return new CustomResponse(true, null, phone);
    }

    @DeleteMapping(path="/{person_id}/phones/{phone_id}")
    public CustomResponse deletePhone(
            HttpServletResponse response,
            @PathVariable Integer person_id,
            @PathVariable Integer phone_id
    ) {
        Person person = personRepository
                .findById(person_id)
                .orElseThrow(() -> new PersonNotFoundException(person_id));
        Phone phone = phoneRepository
                .findByPersonAndId(person, phone_id)
                .orElseThrow(()->new PhoneNotFoundException(phone_id));
        phoneRepository.delete(phone);
        response.setStatus(204);
        return new CustomResponse(true, null, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomResponse handleValidationExceptions(
            HttpServletResponse response,
            MethodArgumentNotValidException e
    ){
        response.setStatus(400);
        return new CustomResponse(
                false,
                e.getLocalizedMessage(),
                e.getBindingResult().getAllErrors()
        );
    }


}
