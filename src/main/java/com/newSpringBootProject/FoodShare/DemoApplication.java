package com.newSpringBootProject.FoodShare;

import com.newSpringBootProject.FoodShare.student.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping; // Add this import
import org.springframework.web.bind.annotation.RestController; // Add this import


@SpringBootApplication

public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}
