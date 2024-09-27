package com.newSpringBootProject.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
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
        }else{
            System.out.println(student);
            studentRepository.save(student);
        }
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
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId).orElseThrow(()->new IllegalStateException("student doesn't exist"));
        if(name!=null && !name.isEmpty() &&
                !Objects.equals(student.getName(),name) ){
            student.setName(name);
        }
        if(email!=null && !email.isEmpty() &&
                !Objects.equals(student.getEmail(),email) ){
            Optional<Student> studentOptional =studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }

    }
}
