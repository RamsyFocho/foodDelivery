package com.newSpringBootProject.FoodShare.pages;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
@Controller
public class pages{
    // login page
    @GetMapping("/loginPage")
    public String loginPage(HttpSession session){
        Long userId = (Long) session.getAttribute("clientId");
        if(session.getAttribute("role")=="" || userId == null){
            return "/login.html";
        }else{
            if(session.getAttribute("role")=="admin"){
                return "redirect:/adminDashboard.html";
            }else {
                return "redirect:/user/dashboard";
            }
        }

    }

    @GetMapping("/registerPage")
    public String registerPage(){
        return "/registration.html";
    }
    @GetMapping("/admin/dashboard")
    public String adminDashboardPage(HttpSession session){
        if(session.getAttribute("adminId") == null){
           return "redirect:/loginPage";
        }
        return "adminDashboard.html";
    }


    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute("clientId");
        session.removeAttribute("adminId");
        session.removeAttribute("role");
        session.invalidate();
        return "redirect:/loginPage";
    }
    @GetMapping("/services")
    public String ServicesPage(){
        return "service.html";
    }
    @GetMapping("/about")
    public String AboutPage(){
        return "about.html";
    }
    @GetMapping("/inventory")
    public String inventoryPage(HttpSession session){
        Long userId = (Long) session.getAttribute("clientId");
        if(session.getAttribute("role")=="" || userId == null){
            return "/login.html";
        }
        return "inventory.html";
    }

    @GetMapping("/orders")
    public String orderMsPage(HttpSession session){
        if(session.getAttribute("adminId") == null){
            return "redirect:/loginPage";
        }
        return "admin/order.html";
    }
}