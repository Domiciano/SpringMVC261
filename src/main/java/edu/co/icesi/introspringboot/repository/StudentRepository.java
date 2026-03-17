package edu.co.icesi.introspringboot.repository;

import edu.co.icesi.introspringboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByCode(String code);

    List<Student> findByProgram(String program);

    // Lesson 17: Find students enrolled in a course by course name
    List<Student> findByStudentCourses_Course_Name(String courseName);
}
