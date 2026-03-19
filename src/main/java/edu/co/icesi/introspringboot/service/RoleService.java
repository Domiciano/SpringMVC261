package edu.co.icesi.introspringboot.service;

import edu.co.icesi.introspringboot.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role save(Role role);

    List<Role> findAll();

    Optional<Role> findById(Integer id);

    void deleteById(Integer id);
}
