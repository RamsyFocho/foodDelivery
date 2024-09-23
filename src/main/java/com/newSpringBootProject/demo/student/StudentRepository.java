package com.newSpringBootProject.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//data access

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
