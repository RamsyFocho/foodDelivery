package com.newSpringBootProject.FoodShare.repository;

import com.newSpringBootProject.FoodShare.domains.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {
    Optional<Food> findByName(String name);
    @Query("Select f from Food f where f.quantity>0")
    List<Food> findAllByQuantity();
}
