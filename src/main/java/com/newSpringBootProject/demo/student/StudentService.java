package com.newSpringBootProject.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

//service layer
@Service
public class StudentService {
//    declare an object of type StudentRepository
    private final StudentRepository studentRepository;

    //call the student repository interface which has inherited properties from the JpaRepository
    @Autowired
    public  StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }
   //returns all the student
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudents(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("email taken");
        }
        System.out.println(student);
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
       boolean exists= studentRepository.existsById(studentId);
       if(!exists){
           throw new IllegalStateException(
                   "student with id "+studentId+" doesn't exists"
           );
       }
       studentRepository.deleteById(studentId);
    }
}
