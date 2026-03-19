package edu.co.icesi.introspringboot.integration;

import edu.co.icesi.introspringboot.entity.Role;
import edu.co.icesi.introspringboot.repository.RoleRepository;
import edu.co.icesi.introspringboot.service.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RoleServiceIntegrationTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    Role roleTest001;

    @BeforeEach
    void setup() {
        roleTest001 = new Role();
        roleTest001.setName("ROLE_TEST");
        roleRepository.save(roleTest001);
    }

    @AfterEach
    void cleanup() {
        roleRepository.deleteAll();
    }

    // --- save ---

    @Test
    void save_WhenValidRole_ShouldPersistAndReturnWithId() {
        // ARRANGE
        Role newRole = new Role();
        newRole.setName("ROLE_NEW");

        // ACT
        Role saved = roleService.save(newRole);

        // ASSERT
        assertNotNull(saved.getId());
        assertTrue(roleRepository.findById(saved.getId()).isPresent());
    }

    // --- findAll ---

    @Test
    void findAll_WhenRolesExist_ShouldReturnAll() {
        // ACT
        List<Role> result = roleService.findAll();

        // ASSERT
        assertFalse(result.isEmpty());
        assertTrue(result.size() >= 1);
    }

    @Test
    void findAll_WhenNoRoles_ShouldReturnEmptyList() {
        // ARRANGE
        roleRepository.deleteAll();

        // ACT
        List<Role> result = roleService.findAll();

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- findById ---

    @Test
    void findById_WhenExists_ShouldReturnRole() {
        // ACT
        Optional<Role> result = roleService.findById(roleTest001.getId());

        // ASSERT
        assertTrue(result.isPresent());
        assertEquals("ROLE_TEST", result.get().getName());
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmptyOptional() {
        // ACT
        Optional<Role> result = roleService.findById(99999);

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- deleteById ---

    @Test
    void deleteById_WhenExists_ShouldRemoveRole() {
        // ACT
        roleService.deleteById(roleTest001.getId());

        // ASSERT
        assertTrue(roleService.findById(roleTest001.getId()).isEmpty());
    }
}
