package edu.co.icesi.introspringboot.services.impl;

import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import edu.co.icesi.introspringboot.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getCoursesByCredits(int credits, int page, int quantity){
        var pageable = PageRequest.of(page, quantity);
        return courseRepository.findByCreditsEquals(credits, pageable);
    }

}
