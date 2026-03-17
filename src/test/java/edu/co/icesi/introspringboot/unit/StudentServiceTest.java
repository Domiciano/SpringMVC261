package edu.co.icesi.introspringboot.unit;


import edu.co.icesi.introspringboot.entity.Student;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import edu.co.icesi.introspringboot.repository.EnrollmentRepository;
import edu.co.icesi.introspringboot.repository.StudentRepository;
import edu.co.icesi.introspringboot.service.StudentService;
import edu.co.icesi.introspringboot.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;


    private Student student;

    @BeforeEach
    public void setup() {
        student = new Student();
        student.setId(1);
        student.setCode("A001");
        student.setName("Andres Andrade");
        student.setProgram("SIS");

    }

    @Test
    public void findStudentByCode_WhenCodeIsValid_ShouldReturnStudent(){
        //ARRANGE
        when(
                studentRepository.findByCode("A001")
        ).thenReturn(
                Optional.of(student)
        );

        //studentRepository.findByCode("A001"); // --> student
        //ACT
        Student returnedStudent = studentService.findStudentByCode("A001");

        //Assert
        assertEquals(student.getId(), returnedStudent.getId());
        assertEquals(student.getName(), returnedStudent.getName());
        assertEquals(student.getProgram(), returnedStudent.getProgram());

    }

    @Test
    public void findStudentByCode_WhenCodeDoesNotExist_ShouldThrowRuntimeException(){
        //Arrange
        when(
                studentRepository.findByCode("A002")
        ).thenReturn(
                Optional.empty()
        );
        //ACT & ASSERT
        assertThrows(
                RuntimeException.class,
                () -> studentService.findStudentByCode("A002")
        );
    }

    @Test
    public void getStudentsByCourseName_WhenCourseExists_ShouldReturnEnrolledStudents(){
        when(
                courseRepository.existsByName("Ingesoft 4")
        ).thenReturn(
                true
        );
        when(
                studentRepository.findByStudentCourses_Course_Name("Ingesoft 4")
        ).thenReturn(
                List.of(student)
        );
        List<Student> students = studentService.getStudentsByCourseName("Ingesoft 4");
        //Asserts
        assertEquals(students.size(), 1);
        assertEquals(students.get(0).getId(), student.getId());
    }

}
