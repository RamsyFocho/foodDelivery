package com.newSpringBootProject.FoodShare.pages;

import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
@Controller
public class pages{
    // login page
    @GetMapping("/loginPage")
    public String loginPage(){
        return "/login.html";
    }

    @GetMapping("/registerPage")
    public String registerPage(){
        return "/registration.html";
    }
    @GetMapping("/admin/dashboard")
    public String adminDashboardPage(){
        return "adminDashboard.html";
    }
    @GetMapping("/inventory")
    public String inventoryPage(){
        return "inventory.html";
    }
    @GetMapping("/users")
    public String userMsPage(){
        return "admin/users.html";
    }
    @GetMapping("/orders")
    public String orderMsPage(){
        return "admin/order.html";
    }
}