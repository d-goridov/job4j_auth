package ru.job4j.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import static java.util.Collections.emptyList;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;

    public UserDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Метод моделирует объект пользователя, с его основной инеформацией
     * @param login - логин
     * @return - объект пользователя
     * @throws UsernameNotFoundException - если такой пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person person = personRepository.findByLogin(login).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Пользователь с логином " + login + " не зарегистрирован!"));
        return new User(person.getLogin(), person.getPassword(), emptyList());
    }
}
