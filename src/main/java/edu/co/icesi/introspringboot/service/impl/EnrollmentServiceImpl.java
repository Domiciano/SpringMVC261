package edu.co.icesi.introspringboot.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ProfessorRepository professorRepository;

    public EnrollmentServiceImpl(StudentRepository studentRepository,
                                 CourseRepository courseRepository,
                                 EnrollmentRepository enrollmentRepository,
                                 ProfessorRepository professorRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.professorRepository = professorRepository;
    }

    @Transactional
    @Override
    public Enrollment enroll(Integer courseId, Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();

        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    @Override
    public void enrollWithFailure(Integer studentId, Integer courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();

        StudentCourseId id = new StudentCourseId();
        id.setStudentId(studentId);
        id.setCourseId(courseId);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);

        throw new RuntimeException("Error simulado — se aplica rollback");
    }

    @Transactional
    @Override
    public void createCourseWithNewProfessor(String courseName) {
        Professor professor = new Professor();
        professor.setName("Profesor Transitorio");
        professorRepository.save(professor);

        Course course = new Course();
        course.setName(courseName);
        course.setProfessor(professor);

        courseRepository.save(course);
    }

    @Transactional
    @Override
    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> findAll() {
        List<Enrollment> result = new ArrayList<>();
        enrollmentRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Optional<Enrollment> findById(StudentCourseId id) {
        return enrollmentRepository.findById(id);
    }

    @Transactional
    @Override
    public void deleteById(StudentCourseId id) {
        enrollmentRepository.deleteById(id);
    }
}
