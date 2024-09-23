package com.newSpringBootProject.demo;

import com.newSpringBootProject.demo.student.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping; // Add this import
import org.springframework.web.bind.annotation.RestController; // Add this import

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication

public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}
