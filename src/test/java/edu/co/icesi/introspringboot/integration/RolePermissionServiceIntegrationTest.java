package edu.co.icesi.introspringboot.integration;

import edu.co.icesi.introspringboot.entity.Permission;
import edu.co.icesi.introspringboot.entity.Role;
import edu.co.icesi.introspringboot.entity.RolePermission;
import edu.co.icesi.introspringboot.entity.keys.RolePermissionId;
import edu.co.icesi.introspringboot.repository.PermissionRepository;
import edu.co.icesi.introspringboot.repository.RolePermissionRepository;
import edu.co.icesi.introspringboot.repository.RoleRepository;
import edu.co.icesi.introspringboot.service.RolePermissionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RolePermissionServiceIntegrationTest {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    Role roleTest001;
    Permission permissionTest001;
    RolePermission rolePermissionTest001;
    RolePermissionId rolePermissionIdTest001;

    @BeforeEach
    void setup() {
        roleTest001 = new Role();
        roleTest001.setName("ROLE_TEST");
        roleRepository.save(roleTest001);

        permissionTest001 = new Permission();
        permissionTest001.setName("READ_TEST");
        permissionRepository.save(permissionTest001);

        rolePermissionIdTest001 = new RolePermissionId();
        rolePermissionIdTest001.setRoleId(roleTest001.getId());
        rolePermissionIdTest001.setPermissionId(permissionTest001.getId());

        rolePermissionTest001 = new RolePermission();
        rolePermissionTest001.setId(rolePermissionIdTest001);
        rolePermissionTest001.setRole(roleTest001);
        rolePermissionTest001.setPermission(permissionTest001);
        rolePermissionRepository.save(rolePermissionTest001);
    }

    @AfterEach
    void cleanup() {
        rolePermissionRepository.deleteAll();
        roleRepository.deleteAll();
        permissionRepository.deleteAll();
    }

    // --- save ---

    @Test
    void save_WhenValidRolePermission_ShouldPersistAndReturnWithCompositeKey() {
        // ARRANGE - create another role/permission pair
        Role anotherRole = new Role();
        anotherRole.setName("ROLE_OTHER");
        roleRepository.save(anotherRole);

        Permission anotherPermission = new Permission();
        anotherPermission.setName("WRITE_OTHER");
        permissionRepository.save(anotherPermission);

        RolePermissionId newId = new RolePermissionId();
        newId.setRoleId(anotherRole.getId());
        newId.setPermissionId(anotherPermission.getId());

        RolePermission newRolePermission = new RolePermission();
        newRolePermission.setId(newId);
        newRolePermission.setRole(anotherRole);
        newRolePermission.setPermission(anotherPermission);

        // ACT
        RolePermission saved = rolePermissionService.save(newRolePermission);

        // ASSERT
        assertNotNull(saved);
        assertEquals(newId, saved.getId());
        assertTrue(rolePermissionRepository.findById(newId).isPresent());
    }

    // --- findAll ---

    @Test
    void findAll_WhenRolePermissionsExist_ShouldReturnAll() {
        // ACT
        List<RolePermission> result = rolePermissionService.findAll();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findAll_WhenNoRolePermissions_ShouldReturnEmptyList() {
        // ARRANGE
        rolePermissionRepository.deleteAll();

        // ACT
        List<RolePermission> result = rolePermissionService.findAll();

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- findById ---

    @Test
    void findById_WhenExists_ShouldReturnRolePermission() {
        // ACT
        Optional<RolePermission> result = rolePermissionService.findById(rolePermissionIdTest001);

        // ASSERT
        assertTrue(result.isPresent());
        assertEquals(roleTest001.getId(), result.get().getId().getRoleId());
        assertEquals(permissionTest001.getId(), result.get().getId().getPermissionId());
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmptyOptional() {
        // ARRANGE
        RolePermissionId nonExistentId = new RolePermissionId();
        nonExistentId.setRoleId(99999);
        nonExistentId.setPermissionId(99999);

        // ACT
        Optional<RolePermission> result = rolePermissionService.findById(nonExistentId);

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- deleteById ---

    @Test
    void deleteById_WhenExists_ShouldRemoveRolePermission() {
        // ACT
        rolePermissionService.deleteById(rolePermissionIdTest001);

        // ASSERT
        assertTrue(rolePermissionService.findById(rolePermissionIdTest001).isEmpty());
    }
}
