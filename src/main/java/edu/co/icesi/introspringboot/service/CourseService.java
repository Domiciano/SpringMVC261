package edu.co.icesi.introspringboot.service;

import edu.co.icesi.introspringboot.entity.Course;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    // Lesson 15: Pagination
    Page<Course> getCoursesByProfessor(String professorName, int page, int size);

    // Lesson 18: Used in Mockito tests
    List<Course> getAllCourses();

    Course getCourseById(Integer id);

    List<Course> getCoursesByCredits(int credits, int page, int quantity);
}
