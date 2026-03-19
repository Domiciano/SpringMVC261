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
import edu.co.icesi.introspringboot.service.EnrollmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EnrollmentServiceIntegrationTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    Student studentTest001;
    Course courseTest001;
    Professor professorTest001;

    @BeforeEach
    void setup() {
        studentTest001 = new Student();
        studentTest001.setName("John Doe");
        studentTest001.setCode("TEST001");
        studentTest001.setProgram("SIS");
        studentRepository.save(studentTest001);

        professorTest001 = new Professor();
        professorTest001.setName("Marlon Gomez");
        professorRepository.save(professorTest001);

        courseTest001 = new Course();
        courseTest001.setName("Test Course");
        courseTest001.setCredits(3);
        courseTest001.setProfessor(professorTest001);
        courseRepository.save(courseTest001);
    }

    @AfterEach
    void cleanup() {
        enrollmentRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
        professorRepository.deleteAll();
    }

    // --- enroll ---

    @Test
    void enroll_WhenStudentAndCourseExist_ShouldCreateEnrollment() {
        // ACT
        Enrollment enrollment = enrollmentService.enroll(courseTest001.getId(), studentTest001.getId());

        // ASSERT
        assertNotNull(enrollment);
        assertEquals(studentTest001.getId(), enrollment.getId().getStudentId());
        assertEquals(courseTest001.getId(), enrollment.getId().getCourseId());
        assertEquals(1, enrollmentRepository.count());
    }

    @Test
    void enroll_WhenStudentNotFound_ShouldThrowNoSuchElementException() {
        assertThrows(NoSuchElementException.class,
                () -> enrollmentService.enroll(courseTest001.getId(), 99999));
    }

    // --- enrollWithFailure ---

    @Test
    void enrollWithFailure_ShouldRollbackAndNotPersistEnrollment() {
        // ACT & ASSERT
        assertThrows(RuntimeException.class,
                () -> enrollmentService.enrollWithFailure(studentTest001.getId(), courseTest001.getId()));

        // ASSERT - rollback: no enrollment persisted
        assertEquals(0, enrollmentRepository.count());
    }

    // --- createCourseWithNewProfessor ---

    @Test
    void createCourseWithNewProfessor_WhenNameProvided_ShouldPersistCourseAndProfessor() {
        // ACT
        enrollmentService.createCourseWithNewProfessor("Brand New Course");

        // ASSERT
        assertTrue(courseRepository.existsByName("Brand New Course"));
    }

    // --- save ---

    @Test
    void save_WhenValidEnrollment_ShouldPersist() {
        // ARRANGE
        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentTest001.getId());
        id.setCourseId(courseTest001.getId());
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(studentTest001);
        enrollment.setCourse(courseTest001);

        // ACT
        Enrollment saved = enrollmentService.save(enrollment);

        // ASSERT
        assertNotNull(saved);
        assertEquals(1, enrollmentRepository.count());
    }

    // --- findAll ---

    @Test
    void findAll_WhenEnrollmentsExist_ShouldReturnAll() {
        // ARRANGE
        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentTest001.getId());
        id.setCourseId(courseTest001.getId());
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(studentTest001);
        enrollment.setCourse(courseTest001);
        enrollmentRepository.save(enrollment);

        // ACT
        List<Enrollment> result = enrollmentService.findAll();

        // ASSERT
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void findAll_WhenNoEnrollments_ShouldReturnEmptyList() {
        // ACT
        List<Enrollment> result = enrollmentService.findAll();

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- findById ---

    @Test
    void findById_WhenExists_ShouldReturnEnrollment() {
        // ARRANGE
        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentTest001.getId());
        id.setCourseId(courseTest001.getId());
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(studentTest001);
        enrollment.setCourse(courseTest001);
        enrollmentRepository.save(enrollment);

        // ACT
        Optional<Enrollment> result = enrollmentService.findById(id);

        // ASSERT
        assertTrue(result.isPresent());
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmptyOptional() {
        // ARRANGE
        StudentCourseId nonExistentId = new StudentCourseId();
        nonExistentId.setStudentId(99999);
        nonExistentId.setCourseId(99999);

        // ACT
        Optional<Enrollment> result = enrollmentService.findById(nonExistentId);

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- deleteById ---

    @Test
    void deleteById_WhenExists_ShouldRemoveEnrollment() {
        // ARRANGE
        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentTest001.getId());
        id.setCourseId(courseTest001.getId());
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(studentTest001);
        enrollment.setCourse(courseTest001);
        enrollmentRepository.save(enrollment);

        // ACT
        enrollmentService.deleteById(id);

        // ASSERT
        assertEquals(0, enrollmentRepository.count());
    }
}
