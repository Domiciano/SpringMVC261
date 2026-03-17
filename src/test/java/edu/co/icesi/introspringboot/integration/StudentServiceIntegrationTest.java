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
        // Estudiante de prueba
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




    @Test
    public void findStudentByCode_WhenCodeIsValid_ShouldReturnStudent() {
        Student student = studentService.findStudentByCode("TEST001");
        assertNotNull(student);
        assertEquals("John Doe", student.getName());
        assertEquals("SIS", student.getProgram());
    }

    @Test
    public void findStudentByCode_WhenCodeDoesNotExist_ShouldThrowRuntimeException(){
        assertThrows(
                RuntimeException.class,
                () -> studentService.findStudentByCode("TESTXX1")
        );
    }

    @Test
    public void findStudentByCode_WhenCodeIsNull_ShouldThrowIllegalArgumentException(){
        assertThrows(
                IllegalArgumentException.class,
                () -> studentService.findStudentByCode(null)
        );
    }

    @Test
    void getStudentsByCourseName_WhenCourseExists_ShouldReturnEnrolledStudents() {
        //ARRANGE
        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentTest001.getId());
        id.setCourseId(courseTest001.getId());
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(studentTest001);
        enrollment.setCourse(courseTest001);
        enrollmentRepository.save(enrollment);

        //ACT
        List<Student> students = studentService.getStudentsByCourseName("Test Course");

        //ASSERT
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
    }

    @Test
    public void getStudentsByCourseName_WhenCourseHasNoStudents_ShouldReturnEmptyList(){
        //ACT
        List<Student> students = studentService.getStudentsByCourseName("Test Course");
        //ASSERT
        assertTrue(students.isEmpty());
    }

    @AfterEach
    public void cleanup(){
        studentRepository.deleteAll();
        courseRepository.deleteAll();
        professorRepository.deleteAll();
    }


}
