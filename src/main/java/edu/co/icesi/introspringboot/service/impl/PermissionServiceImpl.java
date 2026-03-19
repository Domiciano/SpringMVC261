package edu.co.icesi.introspringboot.service.impl;

import edu.co.icesi.introspringboot.entity.Permission;
import edu.co.icesi.introspringboot.repository.PermissionRepository;
import edu.co.icesi.introspringboot.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public List<Permission> findAll() {
        List<Permission> result = new ArrayList<>();
        permissionRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Optional<Permission> findById(Integer id) {
        return permissionRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        permissionRepository.deleteById(id);
    }
}
