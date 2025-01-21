package com.newSpringBootProject.FoodShare.services;

import com.newSpringBootProject.FoodShare.domains.Food;
import com.newSpringBootProject.FoodShare.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServices {
    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAllFoodItems() {
        return foodRepository.findAll();
    }

    public void addNewFood(Food newfood) {
        foodRepository.save(newfood);
    }
}
