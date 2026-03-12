package edu.co.icesi.introspringboot.unit;

import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.entity.Professor;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import edu.co.icesi.introspringboot.service.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course1;
    private Course course2;
    private Professor professor;

    @BeforeEach
    void setup() {
        professor = new Professor();
        professor.setId(1);
        professor.setName("Juan Perez");

        course1 = new Course();
        course1.setId(1);
        course1.setName("Introduccion a la Programacion");
        course1.setCredits(4);
        course1.setProfessor(professor);

        course2 = new Course();
        course2.setId(2);
        course2.setName("Estructuras de Datos");
        course2.setCredits(4);
        course2.setProfessor(professor);
    }

    // --- getAllCourses ---

    @Test
    void getAllCourses_ReturnsAllCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(course1, course2));

        List<Course> result = courseService.getAllCourses();

        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
    }

    // --- getCourseById ---

    @Test
    void getCourseById_WhenExists_ReturnsCourse() {
        when(courseRepository.findById(1)).thenReturn(Optional.of(course1));

        Course result = courseService.getCourseById(1);

        assertNotNull(result);
        assertEquals("Introduccion a la Programacion", result.getName());
        verify(courseRepository, times(1)).findById(1);
    }

    @Test
    void getCourseById_WhenNotExists_ThrowsRuntimeException() {
        when(courseRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> courseService.getCourseById(99));
        verify(courseRepository, times(1)).findById(99);
    }

    // --- getCoursesByProfessor ---

    @Test
    void getCoursesByProfessor_ReturnsPaginatedResults() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Course> page = new PageImpl<>(List.of(course1, course2), pageable, 2);
        when(courseRepository.findByProfessor_Name("Juan Perez", pageable)).thenReturn(page);

        Page<Course> result = courseService.getCoursesByProfessor("Juan Perez", 0, 5);

        assertEquals(2, result.getTotalElements());
        verify(courseRepository, times(1)).findByProfessor_Name("Juan Perez", pageable);
    }
}
