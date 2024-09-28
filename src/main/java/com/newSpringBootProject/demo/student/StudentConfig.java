package com.newSpringBootProject.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
//Data Access Layer
@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
//create instances of the student object

//            saving the instances in the database
//            repository.saveAll(
//                    List.of(ramsy,alex)
//            );
        };
    }
}
