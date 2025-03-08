package com.newSpringBootProject.FoodShare.domains;

import com.fasterxml.jackson.annotation.*;
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
        property = "id"
)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference("user_id")  // Prevent serialization of the user in this object
//    @JsonBackReference
    private User user;
    private String location;
    private LocalDate orderDate;
    private String status;//pending, processing, completed, cancelled
    private Double total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<FoodOrder> foodOrder;

    public Order() {
    }

    public Order(User user,String location, LocalDate orderDate, String status, Double total, List<FoodOrder> foodOrder) {
        this.user = user;
        this.location = location;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
        this.foodOrder = foodOrder;
    }

    public Order(long id, User user,String location, LocalDate orderDate, String status, Double total, List<FoodOrder> foodOrder) {
        this.id = id;
        this.user = user;
        this.location = location;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
        this.foodOrder = foodOrder;
    }

    public Order(User user, String location, LocalDate orderDate, String status, Double total) {
        this.user = user;
        this.location = location;
        this.orderDate = orderDate;
        this.status = status;
        this.total = total;
    }

    public Order(Double total, LocalDate orderDate) {
        this.total = total;
        this.orderDate = orderDate;
    }
}
