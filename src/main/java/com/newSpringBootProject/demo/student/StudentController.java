package com.newSpringBootProject.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Student.html") // Parent path for all student-related endpoints
public class StudentController {

    private final StudentService studentService;
    Student student= new Student();

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    // Method to display the list of students (mapped to GET requests on /students)
    @GetMapping
    public String getStudents(Model model) {
        List<Student> studentList = studentService.getStudents();
        model.addAttribute("studentList", studentList);
        model.addAttribute("age",student.getAge());
        return "Student"; // This should match the name of your Thymeleaf template without extension (e.g., Student.html)
    }

    // Method to handle student registration (mapped to POST requests on /students/register)
    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student, Model model) {
        studentService.addNewStudents(student);
        return "redirect:/Student"; // Redirect to the students list after registration
    }

    // Method to delete a student (mapped to DELETE requests on /students/{studentId})
    @DeleteMapping("/{studentId}")
    public String deleteStudent(@PathVariable("studentId") Long studentId, Model model) {
        studentService.deleteStudent(studentId);
        return "redirect:/Student"; // Redirect to the students list after deletion
    }

    // Method to update student details (mapped to PUT requests on /students/update/{studentId})
    @PutMapping("/update/{studentId}")
    public String updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
        return "redirect:/Student"; // Redirect to the students list after update
    }
}
