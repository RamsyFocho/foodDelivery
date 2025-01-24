package com.newSpringBootProject.FoodShare.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    private LocalDate orderDate;
    private String status;
    private Double total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<FoodOrder> foodOrder;

    public Order() {
    }

    public Order(User user, LocalDate orderDate, String status, Double total, List<FoodOrder> foodOrder) {
        this.user = user;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
        this.foodOrder = foodOrder;
    }

    public Order(long id, User user, LocalDate orderDate, String status, Double total, List<FoodOrder> foodOrder) {
        Id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
        this.foodOrder = foodOrder;
    }

    public Order(User user, LocalDate orderDate, String status, Double total) {
        this.user = user;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
    }

    public Order(Double total, LocalDate orderDate) {
        this.total = total;
        this.orderDate = orderDate;
    }
}
