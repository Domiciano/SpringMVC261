package edu.co.icesi.introspringboot.integration;

import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.entity.Enrollment;
import edu.co.icesi.introspringboot.entity.Professor;
import edu.co.icesi.introspringboot.entity.Student;
import edu.co.icesi.introspringboot.entity.keys.StudentCourseId;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import edu.co.icesi.introspringboot.repository.EnrollmentRepository;
import edu.co.icesi.introspringboot.repository.ProfessorRepository;
import edu.co.icesi.introspringboot.repository.StudentRepository;
import edu.co.icesi.introspringboot.service.StudentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentServiceIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    private Student testStudent;
    private Course testCourse;
    private Professor testProfessor;

    @BeforeEach
    void setup() {
        testProfessor = new Professor();
        testProfessor.setName("Profesor Test");
        professorRepository.save(testProfessor);

        testCourse = new Course();
        testCourse.setName("Curso Test");
        testCourse.setCredits(3);
        testCourse.setProfessor(testProfessor);
        courseRepository.save(testCourse);

        testStudent = new Student();
        testStudent.setName("Estudiante Test");
        testStudent.setCode("TEST001");
        testStudent.setProgram("Ingenieria");
        studentRepository.save(testStudent);
    }

    @AfterEach
    void tearDown() {
        enrollmentRepository.deleteAll();
        studentRepository.findByCode("TEST001").ifPresent(studentRepository::delete);
        courseRepository.findByName("Curso Test").forEach(courseRepository::delete);
        professorRepository.delete(testProfessor);
    }

    // --- findStudentByCode ---

    @Test
    void findStudentByCode_WhenExists_ReturnsStudent() {
        Student found = studentService.findStudentByCode("TEST001");
        assertNotNull(found);
        assertEquals("Estudiante Test", found.getName());
    }

    @Test
    void findStudentByCode_WhenNotExists_ThrowsRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> studentService.findStudentByCode("NOEXISTE"));
    }

    @Test
    void findStudentByCode_WhenCodeIsBlank_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> studentService.findStudentByCode(""));
    }

    // --- getStudentsByCourseName ---

    @Test
    void getStudentsByCourseName_WhenCourseHasStudents_ReturnsStudents() {
        StudentCourseId id = new StudentCourseId();
        id.setStudentId(testStudent.getId());
        id.setCourseId(testCourse.getId());
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(testStudent);
        enrollment.setCourse(testCourse);
        enrollmentRepository.save(enrollment);

        List<Student> students = studentService.getStudentsByCourseName("Curso Test");

        assertFalse(students.isEmpty());
        assertTrue(students.stream().anyMatch(s -> s.getCode().equals("TEST001")));
    }

    @Test
    void getStudentsByCourseName_WhenCourseNotExists_ThrowsRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> studentService.getStudentsByCourseName("Curso Inexistente"));
    }

    // --- deleteStudentByCode ---

    @Test
    void deleteStudentByCode_WhenExists_DeletesStudent() {
        studentService.deleteStudentByCode("TEST001");
        assertFalse(studentRepository.findByCode("TEST001").isPresent());

        // Recreate so tearDown doesn't fail
        testStudent = new Student();
        testStudent.setName("Estudiante Test");
        testStudent.setCode("TEST001");
        testStudent.setProgram("Ingenieria");
        studentRepository.save(testStudent);
    }

    @Test
    void deleteStudentByCode_WhenNotExists_ThrowsRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> studentService.deleteStudentByCode("NOEXISTE"));
    }

    // --- enrollStudentInCourse ---

    @Test
    void enrollStudentInCourse_WhenValid_CreatesEnrollment() {
        studentService.enrollStudentInCourse("TEST001", "Curso Test");
        assertTrue(enrollmentRepository.existsByStudentAndCourse(testStudent, testCourse));
    }

    @Test
    void enrollStudentInCourse_WhenAlreadyEnrolled_ThrowsRuntimeException() {
        studentService.enrollStudentInCourse("TEST001", "Curso Test");
        assertThrows(RuntimeException.class,
                () -> studentService.enrollStudentInCourse("TEST001", "Curso Test"));
    }

    // --- unenrollStudentFromCourse ---

    @Test
    void unenrollStudentFromCourse_WhenEnrolled_RemovesEnrollment() {
        studentService.enrollStudentInCourse("TEST001", "Curso Test");
        studentService.unenrollStudentFromCourse("TEST001", "Curso Test");
        assertFalse(enrollmentRepository.existsByStudentAndCourse(testStudent, testCourse));
    }

    @Test
    void unenrollStudentFromCourse_WhenNotEnrolled_ThrowsRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> studentService.unenrollStudentFromCourse("TEST001", "Curso Test"));
    }
}
