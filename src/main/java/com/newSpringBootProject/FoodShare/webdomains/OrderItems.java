package com.newSpringBootProject.FoodShare.webdomains;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class OrderItems {
    private String name;
    private Integer quantity;
    private Double price;

}
