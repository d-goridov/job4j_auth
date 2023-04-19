package ru.job4j.auth.repository;

import org.springframework.stereotype.Component;
import ru.job4j.auth.domain.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Хранилище пользоваателей,
 * в основе лежит карта
 */
@Component
public class UserStore {

    private final ConcurrentHashMap<String, Person> users = new ConcurrentHashMap<>();

    /**
     * Метод сохраниения пользователя в памяти
     * @param person - сохраняемый пользователь
     */
    public void save(Person person) {
        users.put(person.getLogin(), person);
    }

    /**
     * Метод поиска пользователя по логину
     * @param login - логин
     * @return - объект пользователя
     */
    public Person findByLogin(String login) {
        return users.get(login);
    }

    /**
     * Метод возвращает список всех пользователей
     * @return - объект типа List
     */
    public List<Person> findAll() {
        return new ArrayList<>(users.values());
    }
}
