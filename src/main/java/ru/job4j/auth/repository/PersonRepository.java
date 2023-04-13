package ru.job4j.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.auth.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
