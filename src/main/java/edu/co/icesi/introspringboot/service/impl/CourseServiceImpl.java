package edu.co.icesi.introspringboot.service.impl;

import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import edu.co.icesi.introspringboot.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
        List<Course> result = new ArrayList<>();
        courseRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Course getCourseById(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
    }

    @Override
    public List<Course> getCoursesByCredits(int credits, int page, int quantity) {
        Pageable pageable = PageRequest.of(page, quantity);
        return courseRepository.findByCreditsEquals(credits, pageable);
    }

    @Override
    public Course save(Course course) {
        //Validaciones
        return courseRepository.save(course);
    }

    @Override
    public void deleteById(Integer id) {
        courseRepository.deleteById(id);
    }
}
