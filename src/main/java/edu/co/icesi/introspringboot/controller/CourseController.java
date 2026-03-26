package edu.co.icesi.introspringboot.controller;


import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.entity.Professor;
import edu.co.icesi.introspringboot.service.CourseService;
import edu.co.icesi.introspringboot.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course") // -> Solo tiene que ver con la URL
public class CourseController {

    private final ProfessorService professorService;
    private CourseService courseService;

    public CourseController(CourseService courseService, ProfessorService professorService) {
        this.courseService = courseService;
        this.professorService = professorService;
    }

    // http://localhost:8080/course/list
    @GetMapping("/list")
    public String list(Model model) {
        List<Course> courses = courseService.getAllCourses();
        List<Professor> professors = professorService.findAll();
        model.addAttribute(
                "courses",
                courses
        );
        model.addAttribute("professors", professors);
        model.addAttribute(
                "newcourse",
                new Course()
        );
        return "course/courselist";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Course course) {
        courseService.save(course);
        //Aqui ya puedo gozar de un course con id
        return "redirect:/course/list";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Integer id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "course/detail";
    }

}
