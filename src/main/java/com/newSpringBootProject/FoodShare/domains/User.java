package com.newSpringBootProject.FoodShare.domains;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @Email
    private String email;

    private String password;

    private String phoneNumber;

    private String role; // admin/user
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonManagedReference("id")
//    @JsonManagedReference
    @JsonIgnore
    private List<Order> orders;

    private LocalDate createdDate;
    public User() {
    }

    public User(Long id, String name, String email, String password, String phoneNumber, String role, List<Order> orders, LocalDate createdDate) {
        this.Id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.orders = orders;
        this.createdDate = createdDate;
    }

    public User(String name, String email, String password,String phoneNumber, String role, List<Order> orders) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.orders = orders;
    }

    public User(String name, String email, String password, String phoneNumber, LocalDate createdDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
    }
}
