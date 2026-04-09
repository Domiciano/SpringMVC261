package edu.co.icesi.introspringboot.service;

import edu.co.icesi.introspringboot.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    List<User> findAll();

    Optional<User> findById(Integer id);

    void deleteById(Integer id);

    User findByUsername(String username);
}
