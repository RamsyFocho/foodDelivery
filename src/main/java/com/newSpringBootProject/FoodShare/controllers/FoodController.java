package com.newSpringBootProject.FoodShare.controllers;

import com.newSpringBootProject.FoodShare.domains.Food;
import com.newSpringBootProject.FoodShare.repository.FoodRepository;
import com.newSpringBootProject.FoodShare.services.FoodServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/foodApi")
public class FoodController {
    @Autowired
    private FoodServices foodServices;

    @GetMapping("/food-items")
    @ResponseBody
    public ResponseEntity<?> getAllFoodItems() {
        List <Food> foodList = foodServices.getAllFoodItems();
        System.out.println("FoodController.getAllFoodItems");
        return ResponseEntity.ok( foodList );
    }
    @GetMapping("/food-items/{id}")
    @ResponseBody
    public ResponseEntity<?> getFoodById(@PathVariable("id") Long foodId){
        System.out.println("The id is "+foodId);
        Food food = foodServices.getFoodById(foodId);
        if(food != null){
            System.out.println(food.getName());
            return ResponseEntity.ok(food);
        }
        return null;
    }

    @PostMapping("/food-items")
    @ResponseBody
    public ResponseEntity<?> addFoodItem(@RequestBody Food Newfood){
        List <Food> foodList = foodServices.getAllFoodItems();
        boolean found = false;
        for(Food food : foodList){
            if(Objects.equals(Newfood.getName(),food.getName())){
//                TODO
                found=true;
            }
        }
        if(found){
            return ResponseEntity.ok("Not found");

        }
        foodServices.addNewFood(Newfood);
        return ResponseEntity.ok("food sent");

    }
    @PutMapping("/food-items/{editingId}")
    @ResponseBody
    public ResponseEntity<?> updateFood(@PathVariable Long editingId, @RequestBody Food updateFood){
        System.out.println("The food Id in the put is "+editingId);
        boolean done = foodServices.updateFoodById(editingId,updateFood);
        if(done){
            System.out.println("updated?");
            return ResponseEntity.ok("food updated");
        }
        return ResponseEntity.ok("food Not");

    }

}
