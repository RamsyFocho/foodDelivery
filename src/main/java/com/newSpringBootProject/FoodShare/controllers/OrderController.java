package com.newSpringBootProject.FoodShare.controllers;

import com.newSpringBootProject.FoodShare.domains.Food;
import com.newSpringBootProject.FoodShare.domains.FoodOrder;
import com.newSpringBootProject.FoodShare.domains.Order;
import com.newSpringBootProject.FoodShare.domains.User;
import com.newSpringBootProject.FoodShare.services.*;
import com.newSpringBootProject.FoodShare.webdomains.OrderItems;
import com.newSpringBootProject.FoodShare.webdomains.OrderRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserServices userServices;
    @Autowired
    private FoodServices foodServices;
    @Autowired
    private FoodOrderService foodOrderService;
    @Autowired
    private SmsService smsService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, HttpSession session) {
        Long clientId = (Long)session.getAttribute("clientId");
        User user = userServices.getUserById(clientId);
        String phoneNumber =  user.getPhoneNumber();
        session.setAttribute("phoneNumber", phoneNumber);

        // Extract data from the request
        List<OrderItems> orderItems = orderRequest.getItems();
        String location = orderRequest.getLocation();

        double total = 0.0;
        for(OrderItems orders: orderItems){
            total += (orders.getPrice() * orders.getQuantity());
        }

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setOrderDate(LocalDate.from(LocalDateTime.now()));
        newOrder.setStatus("ACTIVE");
        newOrder.setTotal(total);
        newOrder.setLocation(location);
        orderService.addOrder(newOrder);

//        add now the Food items
        for(OrderItems orders: orderItems){
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
    @GetMapping("/recent")
    public ResponseEntity<?> getOrders(){
        List<Order> order = orderService.getAllOrder();
        for(Order or:order){
            System.out.println(or.getUser().getName());
//            or.getUser().getName();
        }
        return ResponseEntity.ok(orderService.getAllOrder());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}/{status}")
    public ResponseEntity<?> setStatus(@PathVariable("id") Long id,@PathVariable("status") String status) {
        System.out.println("------------------the id is "+id);
        System.out.println("status is "+status);
        if(Objects.equals(id,null) || Objects.equals(status,null)){
            return ResponseEntity.noContent().build();
        }
        boolean done = orderService.setStatusById(id,status);
        if(done){
            User user = orderService.getOrderById(id).getUser();
            if(status.equals("PROCESSING")){
    //            send message to the client via twilio to send the money via the admin's address
                String phoneNumber = user.getPhoneNumber();
                System.out.println(phoneNumber);
                String message = "Thanks for Placing your order using the RamsyDeliverySystem. Your order would be here soon. Please prcodeed by sending the money to this MOMO account (default momo number and name)";
                smsService.sendSms(phoneNumber, message);
            }
            return ResponseEntity.ok("ok");

        }else {
            return ResponseEntity.notFound().build();
        }

    }
}
