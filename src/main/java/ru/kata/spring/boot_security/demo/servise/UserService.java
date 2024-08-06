package ru.kata.spring.boot_security.demo.servise;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    void addUser(User user);

    User getUser(long id);

    void updateUser(User user);

    void deleteUser(long id);

    void addInitUsers();

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
