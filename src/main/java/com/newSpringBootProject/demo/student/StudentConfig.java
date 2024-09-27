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
            Student ramsy = new Student(
                        "Ramsy",
                        "ramsy@gmail.com",
                        LocalDate.of(2006, Month.FEBRUARY,10)
            );
            Student alex= new Student(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(2003, Month.MAY,10)
            );
//            saving the instances in the database
//            repository.saveAll(
//                    List.of(ramsy,alex)
//            );
        };
    }
}
