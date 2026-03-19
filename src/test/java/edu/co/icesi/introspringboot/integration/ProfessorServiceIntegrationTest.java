package edu.co.icesi.introspringboot.integration;

import edu.co.icesi.introspringboot.entity.Professor;
import edu.co.icesi.introspringboot.repository.ProfessorRepository;
import edu.co.icesi.introspringboot.service.ProfessorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProfessorServiceIntegrationTest {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorRepository professorRepository;

    Professor professorTest001;

    @BeforeEach
    void setup() {
        professorTest001 = new Professor();
        professorTest001.setName("Prof Test");
        professorRepository.save(professorTest001);
    }

    @AfterEach
    void cleanup() {
        professorRepository.deleteAll();
    }

    // --- saveProfessor ---

    @Test
    void saveProfessor_WhenValid_ShouldPersistAndReturnWithId() {
        // ARRANGE
        Professor newProfessor = new Professor();
        newProfessor.setName("New Professor");

        // ACT
        Professor saved = professorService.saveProfessor(newProfessor);

        // ASSERT
        assertNotNull(saved.getId());
        assertTrue(professorRepository.findById(saved.getId()).isPresent());
    }

    // --- findAll ---

    @Test
    void findAll_WhenProfessorsExist_ShouldReturnAll() {
        // ACT
        List<Professor> result = professorService.findAll();

        // ASSERT
        assertFalse(result.isEmpty());
        assertTrue(result.size() >= 1);
    }

    @Test
    void findAll_WhenNoProfessors_ShouldReturnEmptyList() {
        // ARRANGE
        professorRepository.deleteAll();

        // ACT
        List<Professor> result = professorService.findAll();

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- findById ---

    @Test
    void findById_WhenExists_ShouldReturnProfessor() {
        // ACT
        Optional<Professor> result = professorService.findById(professorTest001.getId());

        // ASSERT
        assertTrue(result.isPresent());
        assertEquals("Prof Test", result.get().getName());
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmptyOptional() {
        // ACT
        Optional<Professor> result = professorService.findById(99999);

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- deleteById ---

    @Test
    void deleteById_WhenExists_ShouldRemoveProfessor() {
        // ACT
        professorService.deleteById(professorTest001.getId());

        // ASSERT
        assertTrue(professorService.findById(professorTest001.getId()).isEmpty());
    }
}
