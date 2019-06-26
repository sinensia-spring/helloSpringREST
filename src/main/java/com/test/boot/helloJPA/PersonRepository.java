package com.test.boot.helloJPA;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(excerptProjection = InlinePhones.class)
public interface PersonRepository extends CrudRepository<Person, Integer> {
}