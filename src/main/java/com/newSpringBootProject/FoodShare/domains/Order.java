package com.newSpringBootProject.FoodShare.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "`order`") // Escapes the reserved keyword
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "Id"
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private Integer quantity;

    private LocalDate orderDate;

    public Order(long id, User user, Food food, Integer quantity, LocalDate orderDate) {
        Id = id;
        this.user = user;
        this.food = food;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public Order(User user, Food food, Integer quantity, LocalDate orderDate) {
        this.user = user;
        this.food = food;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

}
