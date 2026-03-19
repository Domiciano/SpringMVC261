package edu.co.icesi.introspringboot.integration;

import edu.co.icesi.introspringboot.entity.Role;
import edu.co.icesi.introspringboot.entity.User;
import edu.co.icesi.introspringboot.entity.UserRole;
import edu.co.icesi.introspringboot.entity.keys.UserRoleId;
import edu.co.icesi.introspringboot.repository.RoleRepository;
import edu.co.icesi.introspringboot.repository.UserRepository;
import edu.co.icesi.introspringboot.repository.UserRoleRepository;
import edu.co.icesi.introspringboot.service.UserRoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRoleServiceIntegrationTest {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    User userTest001;
    Role roleTest001;
    UserRole userRoleTest001;
    UserRoleId userRoleIdTest001;

    @BeforeEach
    void setup() {
        userTest001 = new User();
        userTest001.setUsername("testuser");
        userTest001.setPassword("password");
        userRepository.save(userTest001);

        roleTest001 = new Role();
        roleTest001.setName("ROLE_TEST");
        roleRepository.save(roleTest001);

        userRoleIdTest001 = new UserRoleId();
        userRoleIdTest001.setUserId(userTest001.getId());
        userRoleIdTest001.setRoleId(roleTest001.getId());

        userRoleTest001 = new UserRole();
        userRoleTest001.setId(userRoleIdTest001);
        userRoleTest001.setUser(userTest001);
        userRoleTest001.setRole(roleTest001);
        userRoleRepository.save(userRoleTest001);
    }

    @AfterEach
    void cleanup() {
        userRoleRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    // --- save ---

    @Test
    void save_WhenValidUserRole_ShouldPersistAndReturnWithCompositeKey() {
        // ARRANGE - create another user/role pair
        User anotherUser = new User();
        anotherUser.setUsername("anotheruser");
        anotherUser.setPassword("pass");
        userRepository.save(anotherUser);

        Role anotherRole = new Role();
        anotherRole.setName("ROLE_OTHER");
        roleRepository.save(anotherRole);

        UserRoleId newId = new UserRoleId();
        newId.setUserId(anotherUser.getId());
        newId.setRoleId(anotherRole.getId());

        UserRole newUserRole = new UserRole();
        newUserRole.setId(newId);
        newUserRole.setUser(anotherUser);
        newUserRole.setRole(anotherRole);

        // ACT
        UserRole saved = userRoleService.save(newUserRole);

        // ASSERT
        assertNotNull(saved);
        assertEquals(newId, saved.getId());
        assertTrue(userRoleRepository.findById(newId).isPresent());
    }

    // --- findAll ---

    @Test
    void findAll_WhenUserRolesExist_ShouldReturnAll() {
        // ACT
        List<UserRole> result = userRoleService.findAll();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findAll_WhenNoUserRoles_ShouldReturnEmptyList() {
        // ARRANGE
        userRoleRepository.deleteAll();

        // ACT
        List<UserRole> result = userRoleService.findAll();

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- findById ---

    @Test
    void findById_WhenExists_ShouldReturnUserRole() {
        // ACT
        Optional<UserRole> result = userRoleService.findById(userRoleIdTest001);

        // ASSERT
        assertTrue(result.isPresent());
        assertEquals(userTest001.getId(), result.get().getId().getUserId());
        assertEquals(roleTest001.getId(), result.get().getId().getRoleId());
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmptyOptional() {
        // ARRANGE
        UserRoleId nonExistentId = new UserRoleId();
        nonExistentId.setUserId(99999);
        nonExistentId.setRoleId(99999);

        // ACT
        Optional<UserRole> result = userRoleService.findById(nonExistentId);

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- deleteById ---

    @Test
    void deleteById_WhenExists_ShouldRemoveUserRole() {
        // ACT
        userRoleService.deleteById(userRoleIdTest001);

        // ASSERT
        assertTrue(userRoleService.findById(userRoleIdTest001).isEmpty());
    }
}
