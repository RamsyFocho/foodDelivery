package com.newSpringBootProject.FoodShare.webdomains;

import java.util.List;

public class OrderRequest {
    private List<OrderItems> items;
    private String location;

    // Getters and setters
    public List<OrderItems> getItems() {
        return items;
    }

    public void setItems(List<OrderItems> items) {
        this.items = items;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
