package com.newSpringBootProject.demo.student;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
//api later

@RestController //it will handle HTTP requests and return data (usually in JSON format).
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return  studentService.getStudents();
    }
    @PostMapping
    public void registerStudent(@RequestBody Student student){//taking the student from the request body
        studentService.addNewStudents(student);
    }

}
