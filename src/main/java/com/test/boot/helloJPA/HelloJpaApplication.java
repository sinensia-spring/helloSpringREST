package com.test.boot.helloJPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class HelloJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloJpaApplication.class, args);
	}

}
