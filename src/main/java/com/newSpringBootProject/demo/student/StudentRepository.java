package com.newSpringBootProject.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//data access

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    @Query("SELECT s FROM Student WHERE s.email=?1")
//    Optional<Student> findById(Long aLong);
    Optional<Student> findStudentByEmail(String email);
}
