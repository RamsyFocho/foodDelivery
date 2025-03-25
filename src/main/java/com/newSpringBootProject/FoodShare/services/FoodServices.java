package com.newSpringBootProject.FoodShare.services;

import com.newSpringBootProject.FoodShare.domains.Food;
import com.newSpringBootProject.FoodShare.repository.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class FoodServices {
    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAllFoodItems() {

        return foodRepository.findAll();
    }
    public List<Food> getAllFoodItemsWithQuantity() {

        return foodRepository.findAllByQuantity();
    }

    public void addNewFood(Food newfood) {
        foodRepository.save(newfood);
    }

    public Food getFoodById(Long foodId) {
        Optional<Food> foodById = foodRepository.findById(foodId);
        return foodById.orElse(null);
    }

    public boolean updateFoodById(Long foodId, Food updateFood) {
        Food food = getFoodById(foodId);
        if (food != null) {
            // Update food details here
//            check if name already exists
            List <Food> foodList = getAllFoodItems();
            boolean found = false;
            for(Food foods : foodList) {
                if(Objects.equals(updateFood.getName(),foods.getName())) {
                    found = true;
                }
            }
            if(!found){
                food.setName(updateFood.getName());
            }
            food.setCategory(updateFood.getCategory());
            food.setQuantity(updateFood.getQuantity());
            food.setPrice(updateFood.getPrice());
            food.setUnit(updateFood.getUnit());
            food.setExpiryDate(updateFood.getExpiryDate());

            foodRepository.save(food);
            return true;
        }
        return false;
    }

    public Food getFoodByName(String name) {
        Optional<Food> foodByName = foodRepository.findByName(name);
        return foodByName.orElse(null);
    }

    public Boolean deleteFood(Long deleteId) {
        try{
            foodRepository.deleteById(deleteId);
            return true;
        }catch (Exception e){
            log.error("e: ", e);
            return false;
        }
    }
}
