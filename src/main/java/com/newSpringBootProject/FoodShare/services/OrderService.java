package com.newSpringBootProject.FoodShare.services;

import com.newSpringBootProject.FoodShare.domains.Order;
import com.newSpringBootProject.FoodShare.domains.User;
import com.newSpringBootProject.FoodShare.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> findActiveOrders(User user) {
        return orderRepository.findByUserAndStatus(user, "ACTIVE");
    }
    public List<Order> findCompletedOrders(User user) {
        return orderRepository.findByUserAndStatus(user, "COMPLETED");
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}
