package edu.co.icesi.introspringboot.service;

import edu.co.icesi.introspringboot.entity.Enrollment;

public interface EnrollmentService {

    Enrollment enroll(Integer courseId, Integer studentId);

    void enrollWithFailure(Integer studentId, Integer courseId);

    void createCourseWithNewProfessor(String courseName);
}
