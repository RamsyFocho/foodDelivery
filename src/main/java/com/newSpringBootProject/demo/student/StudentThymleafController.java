package com.newSpringBootProject.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentThymleafController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String getStudents(Model model) {
        List<Student> studentList = studentService.getStudents();
        model.addAttribute("studentList", studentList);
        return "index";
    }
}
