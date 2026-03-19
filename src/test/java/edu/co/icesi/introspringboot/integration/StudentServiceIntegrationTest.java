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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentServiceIntegrationTest {

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


    Student studentTest001;
    Course courseTest001;
    Professor professorTest001;

    @BeforeEach
    public void setup() {
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
    public void cleanup() {
        enrollmentRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
        professorRepository.deleteAll();
    }

    // --- findStudentByCode ---

    @Test
    public void findStudentByCode_WhenCodeIsValid_ShouldReturnStudent() {
        Student student = studentService.findStudentByCode("TEST001");
        assertNotNull(student);
        assertEquals("John Doe", student.getName());
        assertEquals("SIS", student.getProgram());
    }

    @Test
    public void findStudentByCode_WhenCodeDoesNotExist_ShouldThrowRuntimeException() {
        assertThrows(
                RuntimeException.class,
                () -> studentService.findStudentByCode("TESTXX1")
        );
    }

    @Test
    public void findStudentByCode_WhenCodeIsNull_ShouldThrowIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> studentService.findStudentByCode(null)
        );
    }

    // --- getStudentsByCourseName ---

    @Test
    void getStudentsByCourseName_WhenCourseExists_ShouldReturnEnrolledStudents() {
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
        List<Student> students = studentService.getStudentsByCourseName("Test Course");

        // ASSERT
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
    }

    @Test
    public void getStudentsByCourseName_WhenCourseHasNoStudents_ShouldReturnEmptyList() {
        // ACT
        List<Student> students = studentService.getStudentsByCourseName("Test Course");
        // ASSERT
        assertTrue(students.isEmpty());
    }

    // --- deleteStudentByCode ---

    @Test
    void deleteStudentByCode_WhenStudentExists_ShouldRemoveFromDb() {
        // ACT
        studentService.deleteStudentByCode("TEST001");

        // ASSERT
        assertFalse(studentRepository.findByCode("TEST001").isPresent());
    }

    @Test
    void deleteStudentByCode_WhenStudentNotFound_ShouldThrowRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> studentService.deleteStudentByCode("NOTEXIST"));
    }

    // --- enrollStudentInCourse ---

    @Test
    void enrollStudentInCourse_WhenValidStudentAndCourse_ShouldCreateEnrollment() {
        // ACT
        Enrollment enrollment = studentService.enrollStudentInCourse("TEST001", "Test Course");

        // ASSERT
        assertNotNull(enrollment);
        assertEquals(studentTest001.getId(), enrollment.getId().getStudentId());
        assertEquals(courseTest001.getId(), enrollment.getId().getCourseId());
    }

    @Test
    void enrollStudentInCourse_WhenStudentNotFound_ShouldThrowRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> studentService.enrollStudentInCourse("NOTEXIST", "Test Course"));
    }

    @Test
    void enrollStudentInCourse_WhenAlreadyEnrolled_ShouldThrowIllegalStateException() {
        // ARRANGE - enroll once
        studentService.enrollStudentInCourse("TEST001", "Test Course");

        // ACT & ASSERT - enroll again
        assertThrows(IllegalStateException.class,
                () -> studentService.enrollStudentInCourse("TEST001", "Test Course"));
    }

    // --- unenrollStudentFromCourse ---

    @Test
    void unenrollStudentFromCourse_WhenEnrolled_ShouldDeleteEnrollment() {
        // ARRANGE
        studentService.enrollStudentInCourse("TEST001", "Test Course");

        // ACT
        studentService.unenrollStudentFromCourse("TEST001", "Test Course");

        // ASSERT
        assertEquals(0, enrollmentRepository.count());
    }

    @Test
    void unenrollStudentFromCourse_WhenNotEnrolled_ShouldThrowRuntimeException() {
        assertThrows(RuntimeException.class,
                () -> studentService.unenrollStudentFromCourse("TEST001", "Test Course"));
    }

    // --- save ---

    @Test
    void save_WhenValidStudent_ShouldReturnPersistedStudent() {
        // ARRANGE
        Student newStudent = new Student();
        newStudent.setName("Jane Doe");
        newStudent.setCode("TEST002");
        newStudent.setProgram("SIS");

        // ACT
        Student saved = studentService.save(newStudent);

        // ASSERT
        assertNotNull(saved.getId());
        assertTrue(studentRepository.findById(saved.getId()).isPresent());
    }

    // --- findAll ---

    @Test
    void findAll_WhenStudentsExist_ShouldReturnAll() {
        // ACT
        List<Student> result = studentService.findAll();

        // ASSERT
        assertFalse(result.isEmpty());
        assertTrue(result.size() >= 1);
    }

    @Test
    void findAll_WhenNoStudents_ShouldReturnEmptyList() {
        // ARRANGE
        enrollmentRepository.deleteAll();
        studentRepository.deleteAll();

        // ACT
        List<Student> result = studentService.findAll();

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- findById ---

    @Test
    void findById_WhenExists_ShouldReturnStudent() {
        // ACT
        Optional<Student> result = studentService.findById(studentTest001.getId());

        // ASSERT
        assertTrue(result.isPresent());
        assertEquals("TEST001", result.get().getCode());
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmptyOptional() {
        // ACT
        Optional<Student> result = studentService.findById(99999);

        // ASSERT
        assertTrue(result.isEmpty());
    }

    // --- deleteById ---

    @Test
    void deleteById_WhenExists_ShouldRemoveStudent() {
        // ACT
        studentService.deleteById(studentTest001.getId());

        // ASSERT
        assertTrue(studentService.findById(studentTest001.getId()).isEmpty());
    }
}
