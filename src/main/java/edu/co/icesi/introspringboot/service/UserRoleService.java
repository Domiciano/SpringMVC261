package edu.co.icesi.introspringboot.service;

import edu.co.icesi.introspringboot.entity.UserRole;
import edu.co.icesi.introspringboot.entity.keys.UserRoleId;

import java.util.List;
import java.util.Optional;

public interface UserRoleService {

    UserRole save(UserRole userRole);

    List<UserRole> findAll();

    Optional<UserRole> findById(UserRoleId id);

    void deleteById(UserRoleId id);
}
