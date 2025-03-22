package com.newSpringBootProject.FoodShare.webdomains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class MetaFood {
    private  String name;
    private String Category;
    private Integer quantity;
    private Double price;
    private String unit;
    private LocalDate expiryDate;
    private MultipartFile imageFile;
}
