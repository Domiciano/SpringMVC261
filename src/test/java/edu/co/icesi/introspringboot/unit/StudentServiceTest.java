package edu.co.icesi.introspringboot.unit;

import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.entity.Student;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import edu.co.icesi.introspringboot.repository.EnrollmentRepository;
import edu.co.icesi.introspringboot.repository.StudentRepository;
import edu.co.icesi.introspringboot.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private Course course;

    @BeforeEach
    void setup() {
        student = new Student();
        student.setId(1);
        student.setName("Laura Garcia");
        student.setCode("2021102001");
        student.setProgram("Ingenieria de Sistemas");

        course = new Course();
        course.setId(1);
        course.setName("Estructuras de Datos");
        course.setCredits(4);
    }

    // --- findStudentByCode ---

    @Test
    void findStudentByCode_WhenExists_ReturnsStudent() {
        when(studentRepository.findByCode("2021102001")).thenReturn(Optional.of(student));

        Student result = studentService.findStudentByCode("2021102001");

        assertNotNull(result);
        assertEquals("Laura Garcia", result.getName());
        verify(studentRepository, times(1)).findByCode("2021102001");
    }

    @Test
    void findStudentByCode_WhenNotExists_ThrowsRuntimeException() {
        when(studentRepository.findByCode("NOEXISTE")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> studentService.findStudentByCode("NOEXISTE"));
        verify(studentRepository, times(1)).findByCode("NOEXISTE");
    }

    @Test
    void findStudentByCode_WhenCodeIsBlank_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> studentService.findStudentByCode(""));
        verify(studentRepository, never()).findByCode(any());
    }

    // --- deleteStudentByCode ---

    @Test
    void deleteStudentByCode_WhenExists_DeletesStudent() {
        when(studentRepository.findByCode("2021102001")).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        studentService.deleteStudentByCode("2021102001");

        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void deleteStudentByCode_WhenNotExists_ThrowsRuntimeException() {
        when(studentRepository.findByCode("NOEXISTE")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> studentService.deleteStudentByCode("NOEXISTE"));
        verify(studentRepository, never()).delete(any(Student.class));
    }

    // --- getStudentsByCourseName ---

    @Test
    void getStudentsByCourseName_WhenCourseExists_ReturnsStudents() {
        when(courseRepository.findByName("Estructuras de Datos")).thenReturn(List.of(course));
        when(studentRepository.findByEnrollments_Course_Name("Estructuras de Datos"))
                .thenReturn(List.of(student));

        List<Student> result = studentService.getStudentsByCourseName("Estructuras de Datos");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(studentRepository, times(1)).findByEnrollments_Course_Name("Estructuras de Datos");
    }

    @Test
    void getStudentsByCourseName_WhenCourseNotExists_ThrowsRuntimeException() {
        when(courseRepository.findByName("Curso Inexistente")).thenReturn(List.of());

        assertThrows(RuntimeException.class,
                () -> studentService.getStudentsByCourseName("Curso Inexistente"));
        verify(studentRepository, never()).findByEnrollments_Course_Name(any());
    }
}
