package ru.job4j.auth.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;


@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    public boolean delete(Person person) {
       if (!personRepository.existsById(person.getId())) {
           return false;
       }
       personRepository.delete(person);
       return true;
    }

    @Override
    public boolean update(@RequestBody Person person) {
        if (!personRepository.existsById(person.getId())) {
            return false;
        }
        personRepository.save(person);
        return true;
    }
}
