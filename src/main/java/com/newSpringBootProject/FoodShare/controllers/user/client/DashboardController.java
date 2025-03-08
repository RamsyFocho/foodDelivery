package com.newSpringBootProject.FoodShare.controllers.user.client;

import com.newSpringBootProject.FoodShare.domains.Order;
import com.newSpringBootProject.FoodShare.domains.User;
import com.newSpringBootProject.FoodShare.services.FoodServices;
import com.newSpringBootProject.FoodShare.services.OrderService;
import com.newSpringBootProject.FoodShare.services.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private UserServices userService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private FoodServices foodService;

    @GetMapping("/user/dashboard")
    public String dashboard( Model model, HttpSession session) {
        Long clientId = (Long)session.getAttribute("clientId");
        if(clientId==null){
            return "redirect:/loginPage";
        }
        System.out.println("The client Id is ${clientId}" + clientId);
        User user =userService.getUserById(clientId);
        model.addAttribute("user", user);
        model.addAttribute("activeOrders", orderService.findActiveOrders(user).size());
        model.addAttribute("completedOrders", orderService.findCompletedOrders(user).size());
        model.addAttribute("availableItems", foodService.getAllFoodItemsWithQuantity());
        List <Order> orders = orderService.findByUser(user);
        Collections.reverse(orders);// reverse the object before sending it
        model.addAttribute("orders",orders );

        return "clientDashboard.html";
    }
}
