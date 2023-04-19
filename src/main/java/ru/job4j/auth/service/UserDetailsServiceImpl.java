package ru.job4j.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.UserStore;

import static java.util.Collections.emptyList;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserStore userStore;

    public UserDetailsServiceImpl(UserStore userStore) {
        this.userStore = userStore;
    }

    /**
     * Метод моделирует объект пользователя, с его основной инеформацией
     * @param login - логин
     * @return - объект пользователя
     * @throws UsernameNotFoundException - если такой пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person user = userStore.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        return new User(user.getLogin(), user.getPassword(), emptyList());
    }
}
