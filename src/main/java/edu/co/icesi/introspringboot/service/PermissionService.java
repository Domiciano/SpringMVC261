package edu.co.icesi.introspringboot.service;

import edu.co.icesi.introspringboot.entity.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    Permission save(Permission permission);

    List<Permission> findAll();

    Optional<Permission> findById(Integer id);

    void deleteById(Integer id);
}
