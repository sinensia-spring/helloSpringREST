package com.test.boot.helloJPA;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.LinkedHashSet;


@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @Min(value=0)
    @Max(value=99)
    private Integer age;
    @Pattern(regexp="^view")
    private String company;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Collection<Phone> phones = new LinkedHashSet<Phone>();

    public Person() {}

    public Person(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Collection<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Collection<Phone> phones) {
        this.phones = phones;
    }

}
