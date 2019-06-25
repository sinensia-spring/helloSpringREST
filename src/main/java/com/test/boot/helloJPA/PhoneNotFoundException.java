package com.test.boot.helloJPA;

public class PhoneNotFoundException extends RuntimeException {
    public PhoneNotFoundException(Integer phone_id) {
        super("Phone "+phone_id+" not found");
    }
}
