package edu.co.icesi.introspringboot.service.impl;

import edu.co.icesi.introspringboot.entity.Role;
import edu.co.icesi.introspringboot.repository.RoleRepository;
import edu.co.icesi.introspringboot.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        List<Role> result = new ArrayList<>();
        roleRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }
}
