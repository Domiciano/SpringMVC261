package edu.co.icesi.introspringboot.services.impl;

import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getCoursesByCredits(int credits){

    }

}
