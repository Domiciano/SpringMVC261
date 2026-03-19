package edu.co.icesi.introspringboot.integration;

import edu.co.icesi.introspringboot.entity.Permission;
import edu.co.icesi.introspringboot.repository.PermissionRepository;
import edu.co.icesi.introspringboot.service.PermissionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PermissionServiceIntegrationTest {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionRepository permissionRepository;

    Permission permissionTest001;

    @BeforeEach
    void setup() {
        permissionTest001 = new Permission();
        permissionTest001.setName("READ_TEST");
        permissionRepository.save(permissionTest001);
    }

    @AfterEach
    void cleanup() {
        permissionRepository.deleteAll();
    }

    // --- save ---

    @Test
    void save_WhenValidPermission_ShouldPersistAndReturnWithId() {
        // ARRANGE
        Permission newPermission = new Permission();
        newPermission.setName("WRITE_NEW");

        // ACT
        Permission saved = permissionService.save(newPermission);

        // ASSERT
        assertNotNull(saved.getId());
        assertTrue(permissionRepository.findById(saved.getId()).isPresent());
    }

    // --- findAll ---

    @Test
    void findAll_WhenPermissionsExist_ShouldReturnAll() {
        // ACT
        List<Permission> result = permissionService.findAll();

        // ASSERT
        assertFalse(result.isEmpty());
        assertTrue(result.size() >= 1);
    }

    @Test
    void findAll_WhenNoPermissions_ShouldReturnEmptyList() {
        // ARRANGE
        permissionRepository.deleteAll();

        // ACT
        List<Permission> result = permissionService.findAll();

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- findById ---

    @Test
    void findById_WhenExists_ShouldReturnPermission() {
        // ACT
        Optional<Permission> result = permissionService.findById(permissionTest001.getId());

        // ASSERT
        assertTrue(result.isPresent());
        assertEquals("READ_TEST", result.get().getName());
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmptyOptional() {
        // ACT
        Optional<Permission> result = permissionService.findById(99999);

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- deleteById ---

    @Test
    void deleteById_WhenExists_ShouldRemovePermission() {
        // ACT
        permissionService.deleteById(permissionTest001.getId());

        // ASSERT
        assertTrue(permissionService.findById(permissionTest001.getId()).isEmpty());
    }
}
