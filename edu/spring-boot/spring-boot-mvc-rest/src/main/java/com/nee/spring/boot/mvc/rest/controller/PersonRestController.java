package com.nee.spring.boot.mvc.rest.controller;

import com.nee.spring.boot.mvc.rest.domain.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonRestController {

    @GetMapping("/person/{id}")
    public Person person(@PathVariable Long id,
                         @RequestParam(required = false) String name) {
        Person person = new Person();

        person.setId(id);
        person.setName(name);
        return person;
    }


    @PostMapping(value = "person/json/to/properties",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = "application/properties+person"
    )
    public Person person(@RequestBody Person person) {

        return person;
    }
    @GetMapping(value = "person/properties/to/json",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = "application/properties+person"
    )

    public Person personPropertiesToJson(@RequestBody Person person) {

        return person;
    }


    @GetMapping(value = "noSuchMethod")
    public String noSuchMethod() {

        throw new NullPointerException("null pointer exception");
    }

}
