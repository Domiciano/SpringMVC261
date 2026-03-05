package edu.co.icesi.introspringboot.controller;


import edu.co.icesi.introspringboot.entity.Course;
import edu.co.icesi.introspringboot.entity.Professor;
import edu.co.icesi.introspringboot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class HelloController {

    // http://localhost:8080/hello
    @GetMapping("/hello")
    public String index() {
        return "Hello World 1";
    }

    // http://localhost:8080/hello2
    @GetMapping("/hello2")
    public String index2() {
        return "Hello World 2";
    }

    // http://localhost:8080/hello3
    @GetMapping("/hello3")
    public String index3() {
        return "Hello World 3";
    }
}


