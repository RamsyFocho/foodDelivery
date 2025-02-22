package com.newSpringBootProject.FoodShare.controllers;

import com.newSpringBootProject.FoodShare.domains.Food;
import com.newSpringBootProject.FoodShare.domains.FoodOrder;
import com.newSpringBootProject.FoodShare.domains.Order;
import com.newSpringBootProject.FoodShare.domains.User;
import com.newSpringBootProject.FoodShare.services.FoodOrderService;
import com.newSpringBootProject.FoodShare.services.FoodServices;
import com.newSpringBootProject.FoodShare.services.OrderService;
import com.newSpringBootProject.FoodShare.services.UserServices;
import com.newSpringBootProject.FoodShare.webdomains.OrderItems;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserServices userServices;
    @Autowired
    private FoodServices foodServices;
    @Autowired
    private FoodOrderService foodOrderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody List<OrderItems> order, HttpSession session) {
        Long clientId = (Long)session.getAttribute("clientId");
        User user = userServices.getUserById(clientId);

        double total = 0.0;
        for(OrderItems orders: order){
            total += (orders.getPrice() * orders.getQuantity());
        }

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setOrderDate(LocalDate.from(LocalDateTime.now()));
        newOrder.setStatus("ACTIVE");
        newOrder.setTotal(total);
        orderService.addOrder(newOrder);

//        add now the Food items
        for(OrderItems orders: order){
            FoodOrder foodOrder = new FoodOrder();
            Food food = foodServices.getFoodByName(orders.getName());
//            substract it from the quantity
            food.setQuantity(food.getQuantity()-orders.getQuantity());

            foodOrder.setOrder(newOrder);
            foodOrder.setFood(food);
            foodOrder.setQuantity(orders.getQuantity());
            foodOrder.setPrice(orders.getPrice());

            foodOrderService.addFoodOrder(foodOrder);
        }


//        return ResponseEntity.ok(orderService.addOrder(order));
        return ResponseEntity.ok("test");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
