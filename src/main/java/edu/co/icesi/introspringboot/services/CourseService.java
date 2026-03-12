package edu.co.icesi.introspringboot.services;

import edu.co.icesi.introspringboot.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCoursesByCredits(int credits, int page, int quantity);
}
