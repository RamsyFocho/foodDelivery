package com.newSpringBootProject.FoodShare.domains;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "foodId",nullable = false)
    private Food food;

    private Integer quantity;

    private LocalDate orderDate;
}
