package edu.co.icesi.introspringboot.controller;

import edu.co.icesi.introspringboot.entity.*;
import edu.co.icesi.introspringboot.repository.CourseRepository;
import edu.co.icesi.introspringboot.repository.PermissionRepository;
import edu.co.icesi.introspringboot.repository.ProfessorRepository;
import edu.co.icesi.introspringboot.repository.StudentRepository;
import edu.co.icesi.introspringboot.services.CourseService;
import edu.co.icesi.introspringboot.services.EnrollmentService;
import edu.co.icesi.introspringboot.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ExercisesController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CourseService courseService;

    //http://localhost:8080/icesi/program/23/student/A00234534534

    // http://localhost:8080/courses/page?credits=3&page=0&quantity=5
    @GetMapping("/courses/page")
    public List<Course> getCoursesByCredits(@RequestParam int credits,
                                            @RequestParam int page,
                                            @RequestParam int quantity) {
        return courseService.getCoursesByCredits(credits, page, quantity);
    }


    //http://localhost:8080/example1
    @GetMapping("/exercise1")
    public Student example1() {
        Optional<Student> student = studentRepository.findByCode("2021102001");
        if (student.isPresent()) {
            return student.get();
        }else {
            return null;
        }
    }

    @GetMapping("/exercise2")
    public List<Professor> example2() {
        List<Professor> professorList =  professorRepository.findByNameContainingIgnoreCase("ez");
        return professorList;
    }

    @GetMapping("/exercise3")
    public List<Course> example3() {
        List<Course> courses = courseRepository.findByProfessor_NameOrderByName("Juan Perez");
        return courses;
    }

    @GetMapping("/exercise4")
    public List<Permission> example4() {
        return permissionRepository.findByRolePermissions_Role_UserRoles_User_Username("carlos");
    }


    @GetMapping("/transactiontest")
    public Enrollment transactionTest() {
        return enrollmentService.enroll(5, 2);
    }

    @GetMapping("/savesingleentity")
    public Professor saveProfessor() {
        //Construyo el profesor. En el futuro basado en los parametros de entrada
        Professor professor = new Professor();
        professor.setName("Juan Carlos Cuellar");
        //Almacenemos el profesor
        return professorService.saveProfessor(professor);
    }



}
