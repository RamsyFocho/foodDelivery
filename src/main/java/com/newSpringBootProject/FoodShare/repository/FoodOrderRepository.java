package com.newSpringBootProject.FoodShare.repository;

import com.newSpringBootProject.FoodShare.domains.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder,Long> {
}
