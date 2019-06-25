package com.test.boot.helloJPA;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PhoneRepository extends CrudRepository<Phone, Integer> {
    // Implicit!    Optional<Phone> findById(Integer id);
    List<Phone> findByPerson(Person person);
    Optional<Phone> findByPersonAndId(Person person, Integer id);
}
