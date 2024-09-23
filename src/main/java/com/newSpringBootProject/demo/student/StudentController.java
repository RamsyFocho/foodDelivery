package com.newSpringBootProject.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.ui.Model;
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

//    @GetMapping
//    public String getStudents(Model model) {
//        List<Student> studentList = studentService.getStudents();
//        model.addAttribute("studentList", studentList);
//        return "index";
//    }
    @PostMapping
    public void registerStudent(@RequestBody Student student){//taking the student from the request body
        studentService.addNewStudents(student);
    }
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }
    @PutMapping(path={"studentId"})
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email)
    {
        studentService.updateStudent(studentId,name,email);
    }
}
