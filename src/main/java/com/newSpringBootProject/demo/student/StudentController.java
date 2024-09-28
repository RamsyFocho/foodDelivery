package com.newSpringBootProject.demo.student;

import com.newSpringBootProject.demo.Level.Level;
import com.newSpringBootProject.demo.Level.LevelService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Parent path for all student-related endpoints
public class StudentController {

    private final LevelService levelService; // Declare LevelService
    private final StudentService studentService; // Declare StudentService

    @Autowired
    public StudentController(LevelService levelService, StudentService studentService) {
        this.levelService = levelService; // Inject LevelService
        this.studentService = studentService; // Inject StudentService
    }

    // Method to display the list of students (mapped to GET requests on /students)
    @GetMapping("/students")
    public String getStudents(Model model) {
        List<Student> studentList = studentService.getStudents();
        model.addAttribute("studentList", studentList);
        Student student = new Student();
        model.addAttribute("Update_student",student);

        return "student/Student"; // This should match the name of your Thymeleaf template without extension (e.g., Student.html)
    }
//get the values and display it as a student object model to the post method
    @GetMapping("/create/student")
    public String createStudent(Model model){
        Student student = new Student();
        model.addAttribute("student",student);

        // Fetch levels from your Level service

        List<Level> levels = levelService.getAllLevels(); // Assuming you have a method to get all levels
        model.addAttribute("levels", levels); // Add levels to the model

        return "student/Create_Student";
    }

    // Method to handle student registration (mapped to POST requests on /students/register)
    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student) {
        studentService.addNewStudents(student);
        return "redirect:/students"; // Redirect to the students list after registration
    }

//    get the id from the url using the get method and pass it to the delete function
    @GetMapping("/delete/{studentId}")
    public String StudentId(@PathVariable("studentId") Long studentId) {
        return  deleteStudent(studentId);
    }
    // Method to delete a student (mapped to DELETE requests on /students/{studentId})
    @DeleteMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
        return "redirect:/students"; // Redirect to the students list after deletion
    }
//get the values from the form using the post method and pass it to the put method
    @PostMapping("/update/{studentId}")
    public String handleStudentUpdate(
            @PathVariable("studentId") Long studentId, @ModelAttribute("Update_student") Student student) {

        // Call the PUT method internally or directly update the student here
        return updateStudent(studentId, student);
    }
    // Method to update student details (mapped to PUT requests on /students/update/{studentId}
    @PutMapping("/update/{studentId}")
    public String updateStudent(
            @PathVariable("studentId") Long studentId, @ModelAttribute("Update_student") Student student) {

        studentService.updateStudent(studentId, student.getName(),student.getEmail());
        return "redirect:/students"; // Redirect to the students list after update
    }
}
