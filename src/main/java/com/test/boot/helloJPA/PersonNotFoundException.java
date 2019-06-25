package com.test.boot.helloJPA;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(Integer id) {
        super("Person " + id + " not found");
    }
}
