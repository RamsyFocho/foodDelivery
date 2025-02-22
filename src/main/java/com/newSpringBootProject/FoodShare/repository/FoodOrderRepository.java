package com.newSpringBootProject.FoodShare.repository;

import com.newSpringBootProject.FoodShare.domains.FoodOrder;
import com.newSpringBootProject.FoodShare.domains.Order;
import com.newSpringBootProject.FoodShare.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder,Long> {


}
