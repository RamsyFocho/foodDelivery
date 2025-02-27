package com.newSpringBootProject.FoodShare.repository;

import com.newSpringBootProject.FoodShare.domains.Order;
import com.newSpringBootProject.FoodShare.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
    List<Order> findByUserAndStatus(User user, String status);
    List<Order> findByOrderDateBetween(LocalDate monthAgo, LocalDate now);
}
