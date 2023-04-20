package ru.job4j.auth.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder encoder;

    public UserController(PersonRepository personRepository, BCryptPasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.encoder = encoder;
    }

    /**
     * Метод регистрации пользователя
     * @param person - пользователь
     */
    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        personRepository.save(person);
    }

    /**
     * Метод получения всех пользователей системы
     * @return - список пользователей
     */
    @GetMapping("/all")
    public List<Person> findAll() {
        return personRepository.findAll();
    }
}
