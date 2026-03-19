package edu.co.icesi.introspringboot.service.impl;

import edu.co.icesi.introspringboot.entity.UserRole;
import edu.co.icesi.introspringboot.entity.keys.UserRoleId;
import edu.co.icesi.introspringboot.repository.UserRoleRepository;
import edu.co.icesi.introspringboot.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public Optional<UserRole> findById(UserRoleId id) {
        return userRoleRepository.findById(id);
    }

    @Override
    public void deleteById(UserRoleId id) {
        userRoleRepository.deleteById(id);
    }
}
