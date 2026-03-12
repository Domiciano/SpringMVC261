package edu.co.icesi.introspringboot.service;

import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Moved to service.impl.CourseServiceImpl
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Page<Course> getCoursesByProfessor(String professorName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findByProfessor_Name(professorName, pageable);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
    }
}
