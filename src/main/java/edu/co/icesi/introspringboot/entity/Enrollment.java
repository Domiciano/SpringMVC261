package edu.co.icesi.introspringboot.entity;

import edu.co.icesi.introspringboot.entity.keys.StudentCourseId;
import jakarta.persistence.*;

@Entity
@Table(name = "student_course")
public class Enrollment {

    @EmbeddedId
    private StudentCourseId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    public StudentCourseId getId() { return id; }
    public void setId(StudentCourseId id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}
