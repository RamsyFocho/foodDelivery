package com.newSpringBootProject.FoodShare.domains;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long Id;
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
//    TODO: Implement phoneNumber
    @NotBlank
    private String password;
    @Getter
    @Setter
    private String phoneNumber;
    @NotBlank
    private String role; // admin/user
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonManagedReference("id")
//    @JsonManagedReference
    @JsonIgnore
    private List<Order> orders;
    @Getter
    @Setter
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
    public @NotBlank Long getId() {
        return Id;
    }

    public void setId(@NotBlank Long Id) {
        this.Id = Id;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotBlank String getRole() {
        return role;
    }

    public void setRole(@NotBlank String role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
