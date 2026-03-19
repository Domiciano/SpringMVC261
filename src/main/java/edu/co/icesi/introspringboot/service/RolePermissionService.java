package edu.co.icesi.introspringboot.service;

import edu.co.icesi.introspringboot.entity.RolePermission;
import edu.co.icesi.introspringboot.entity.keys.RolePermissionId;

import java.util.List;
import java.util.Optional;

public interface RolePermissionService {

    RolePermission save(RolePermission rolePermission);

    List<RolePermission> findAll();

    Optional<RolePermission> findById(RolePermissionId id);

    void deleteById(RolePermissionId id);
}
