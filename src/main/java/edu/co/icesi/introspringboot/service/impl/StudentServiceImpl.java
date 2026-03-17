package edu.co.icesi.introspringboot.service.impl;

import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.entity.Enrollment;
import edu.co.icesi.introspringboot.entity.Student;
import edu.co.icesi.introspringboot.entity.keys.StudentCourseId;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import edu.co.icesi.introspringboot.repository.EnrollmentRepository;
import edu.co.icesi.introspringboot.repository.StudentRepository;
import edu.co.icesi.introspringboot.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              CourseRepository courseRepository,
                              EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Student findStudentByCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío");
        }
        return studentRepository.findByCode(code)
                 .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con código: " + code));
    }

    @Override
    public List<Student> getStudentsByCourseName(String courseName) {
        List<Course> courses = courseRepository.findByName(courseName);
        if (courses.isEmpty()) {
            throw new RuntimeException("Curso no encontrado: " + courseName);
        }
        return studentRepository.findByEnrollments_Course_Name(courseName);
    }

    @Override
    @Transactional
    public void deleteStudentByCode(String code) {
        Student student = studentRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con código: " + code));
        studentRepository.delete(student);
    }

    @Override
    @Transactional
    public void enrollStudentInCourse(String studentCode, String courseName) {
        Student student = studentRepository.findByCode(studentCode)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + studentCode));

        List<Course> courses = courseRepository.findByName(courseName);
        if (courses.isEmpty()) {
            throw new RuntimeException("Curso no encontrado: " + courseName);
        }
        Course course = courses.get(0);

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("El estudiante ya está inscrito en este curso");
        }

        StudentCourseId id = new StudentCourseId();
        id.setStudentId(student.getId());
        id.setCourseId(course.getId());

        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);
    }

    @Override
    @Transactional
    public void unenrollStudentFromCourse(String studentCode, String courseName) {
        Student student = studentRepository.findByCode(studentCode)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + studentCode));

        List<Course> courses = courseRepository.findByName(courseName);
        if (courses.isEmpty()) {
            throw new RuntimeException("Curso no encontrado: " + courseName);
        }
        Course course = courses.get(0);

        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new RuntimeException("El estudiante no está inscrito en este curso"));

        enrollmentRepository.delete(enrollment);
    }
}
