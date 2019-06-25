package com.test.boot.helloJPA;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = InlinePhones.class)
public interface PersonRepository extends CrudRepository<Person, Integer> {
}