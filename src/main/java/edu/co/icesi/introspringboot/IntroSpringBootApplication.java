package edu.co.icesi.introspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntroSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntroSpringBootApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println(">>>>>Inició la app");
    }
}
