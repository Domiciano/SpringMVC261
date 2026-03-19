package edu.co.icesi.introspringboot.integration;

import edu.co.icesi.introspringboot.entity.User;
import edu.co.icesi.introspringboot.repository.UserRepository;
import edu.co.icesi.introspringboot.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    User userTest001;

    @BeforeEach
    void setup() {
        userTest001 = new User();
        userTest001.setUsername("testuser");
        userTest001.setPassword("password");
        userRepository.save(userTest001);
    }

    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
    }

    // --- save ---

    @Test
    void save_WhenValidUser_ShouldPersistAndReturnWithId() {
        // ARRANGE
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("newpass");

        // ACT
        User saved = userService.save(newUser);

        // ASSERT
        assertNotNull(saved.getId());
        assertTrue(userRepository.findById(saved.getId()).isPresent());
    }

    // --- findAll ---

    @Test
    void findAll_WhenUsersExist_ShouldReturnAll() {
        // ACT
        List<User> result = userService.findAll();

        // ASSERT
        assertFalse(result.isEmpty());
        assertTrue(result.size() >= 1);
    }

    @Test
    void findAll_WhenNoUsers_ShouldReturnEmptyList() {
        // ARRANGE
        userRepository.deleteAll();

        // ACT
        List<User> result = userService.findAll();

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- findById ---

    @Test
    void findById_WhenExists_ShouldReturnUser() {
        // ACT
        Optional<User> result = userService.findById(userTest001.getId());

        // ASSERT
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmptyOptional() {
        // ACT
        Optional<User> result = userService.findById(99999);

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- deleteById ---

    @Test
    void deleteById_WhenExists_ShouldRemoveUser() {
        // ACT
        userService.deleteById(userTest001.getId());

        // ASSERT
        assertTrue(userService.findById(userTest001.getId()).isEmpty());
    }
}
