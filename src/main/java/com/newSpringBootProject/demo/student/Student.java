package com.newSpringBootProject.demo.student;

import com.newSpringBootProject.demo.Level.Level;
import jakarta.persistence.*;
import jdk.jfr.Enabled;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class Student {
    @Id
    @SequenceGenerator(
            name="student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long Id;
    private  String name;
    private String email;
    private LocalDate dob;
    @Transient
    private Integer age;
    @ManyToOne
    @JoinColumn(name = "level_id", referencedColumnName = "id", nullable = false)
    private Level level;

    public Student() {
    }
//without Id
    public Student(String name, String email, LocalDate dob, Level level) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.level = level;
    }

    public Student(Long id, String name, String email, LocalDate dob, Level level) {
        Id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.level = level;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Integer getAge() {
        return Period.between(this.dob,LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {

    }
//Not yet called
//    @Override
//    public String toString() {
//        return "Student{" +
//                "Id=" + Id +
//                ", name='" + name + '\'' +
//                ", email='" + email + '\'' +
//                ", dob=" + dob +
//                ", age=" + age +
//                '}';
//    }
}
