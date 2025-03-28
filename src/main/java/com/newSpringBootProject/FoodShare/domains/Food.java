package com.newSpringBootProject.FoodShare.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@Setter
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private  String name;
    private String Category;
    private Integer quantity;
    private Double price;
    private String unit;
    private LocalDate expiryDate;
    private String imagePath;


    public Food(Long id, String name, String category, Integer quantity, Double price, String unit, LocalDate expiryDate, String imagePath) {
        Id = id;
        this.name = name;
        Category = category;
        this.quantity = quantity;
        this.price = price;
        this.unit = unit;
        this.expiryDate = expiryDate;
        this.imagePath  = imagePath;
    }

    public Food(String name, String category, Integer quantity, Double price, String unit, LocalDate expiryDate, String imagePath) {
        this.name = name;
        Category = category;
        this.quantity = quantity;
        this.price = price;
        this.unit = unit;
        this.expiryDate = expiryDate;
        this.imagePath  = imagePath;
    }
    public Food(){}
}
