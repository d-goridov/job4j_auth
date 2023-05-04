package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    private final ObjectMapper objectMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());

    public PersonController(PersonService personService, ObjectMapper objectMapper) {
        this.personService = personService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        String login = person.getLogin();
        String password = person.getPassword();
        if (login == null || password == null) {
            throw new NullPointerException("Login and password mustn't be empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Invalid password. Password length must be more than 5 characters.");
        }
        return new ResponseEntity<>(
                personService.create(person),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable("id") int id) {
        Person person = personService.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Person is not exist. Check input data"
                ));
        return new ResponseEntity<>(
                person,
                HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        return personService.update(person) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        Person person = new Person();
        person.setId(id);
        return personService.delete(person) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
