package edu.co.icesi.introspringboot.service;

public interface EnrollmentService {

    void enroll(Integer studentId, Integer courseId);

    void enrollWithFailure(Integer studentId, Integer courseId);

    void createCourseWithNewProfessor(String courseName);
}
