package edu.co.icesi.introspringboot.integration;

import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.entity.Professor;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import edu.co.icesi.introspringboot.repository.ProfessorRepository;
import edu.co.icesi.introspringboot.service.CourseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CourseServiceIntegrationTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    Course courseTest001;
    Professor professorTest001;

    @BeforeEach
    void setup() {
        professorTest001 = new Professor();
        professorTest001.setName("Prof Test");
        professorRepository.save(professorTest001);

        courseTest001 = new Course();
        courseTest001.setName("Test Course");
        courseTest001.setCredits(3);
        courseTest001.setProfessor(professorTest001);
        courseRepository.save(courseTest001);
    }

    @AfterEach
    void cleanup() {
        courseRepository.deleteAll();
        professorRepository.deleteAll();
    }

    // --- getCoursesByProfessor ---

    @Test
    void getCoursesByProfessor_WhenProfessorHasCourses_ShouldReturnPage() {
        // ACT
        Page<Course> result = courseService.getCoursesByProfessor("Prof Test", 0, 10);

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Course", result.getContent().get(0).getName());
    }

    @Test
    void getCoursesByProfessor_WhenProfessorHasNoCourses_ShouldReturnEmptyPage() {
        // ACT
        Page<Course> result = courseService.getCoursesByProfessor("Unknown Professor", 0, 10);

        // ASSERT
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    // --- getAllCourses ---

    @Test
    void getAllCourses_WhenCoursesExist_ShouldReturnList() {
        // ACT
        List<Course> result = courseService.getAllCourses();

        // ASSERT
        assertFalse(result.isEmpty());
        assertTrue(result.size() >= 1);
    }

    @Test
    void getAllCourses_WhenNoCourses_ShouldReturnEmptyList() {
        // ARRANGE
        courseRepository.deleteAll();

        // ACT
        List<Course> result = courseService.getAllCourses();

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- getCourseById ---

    @Test
    void getCourseById_WhenExists_ShouldReturnCourse() {
        // ACT
        Course result = courseService.getCourseById(courseTest001.getId());

        // ASSERT
        assertNotNull(result);
        assertEquals("Test Course", result.getName());
    }

    @Test
    void getCourseById_WhenNotExists_ShouldThrowRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> courseService.getCourseById(99999));
    }

    // --- getCoursesByCredits ---

    @Test
    void getCoursesByCredits_WhenMatchExists_ShouldReturnCourses() {
        // ACT
        List<Course> result = courseService.getCoursesByCredits(3, 0, 10);

        // ASSERT
        assertFalse(result.isEmpty());
        result.forEach(c -> assertEquals(3, c.getCredits()));
    }

    @Test
    void getCoursesByCredits_WhenNoMatch_ShouldReturnEmptyList() {
        // ACT
        List<Course> result = courseService.getCoursesByCredits(99, 0, 10);

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- save ---

    @Test
    void save_WhenValidCourse_ShouldPersistAndReturnWithId() {
        // ARRANGE
        Course newCourse = new Course();
        newCourse.setName("New Course");
        newCourse.setCredits(4);
        newCourse.setProfessor(professorTest001);

        // ACT
        Course saved = courseService.save(newCourse);

        // ASSERT
        assertNotNull(saved.getId());
        assertTrue(courseRepository.findById(saved.getId()).isPresent());
    }

    // --- deleteById ---

    @Test
    void deleteById_WhenExists_ShouldRemoveCourse() {
        // ACT
        courseService.deleteById(courseTest001.getId());

        // ASSERT
        assertFalse(courseRepository.findById(courseTest001.getId()).isPresent());
    }
}
