package com.newSpringBootProject.FoodShare.controllers;

import com.newSpringBootProject.FoodShare.domains.Food;
import com.newSpringBootProject.FoodShare.repository.FoodRepository;
import com.newSpringBootProject.FoodShare.services.FoodServices;
import com.newSpringBootProject.FoodShare.webdomains.MetaFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/foodApi")
public class FoodController {
    @Autowired
    private FoodServices foodServices;
    @Value("${upload.path}") // Set upload folder path in application.properties
    private String uploadPath;

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
    public ResponseEntity<?> addFoodItem(@RequestParam("name") String name,
                                         @RequestParam("category") String category,
                                         @RequestParam("quantity") int quantity,
                                         @RequestParam("price") double price,
                                         @RequestParam("unit") String unit,
                                         @RequestParam("expiryDate") LocalDate expiryDate,
                                         @RequestParam("imageField") MultipartFile imageFile){

        try{
            List <Food> foodList = foodServices.getAllFoodItems();
            boolean found = false;
            for(Food food : foodList){
                if(Objects.equals(name,food.getName())){
    //                TODO
                    found=true;
                }
            }
            if(found){
                return ResponseEntity.ok("Food already exists");

            }
            // Ensure upload directory exists
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate unique file name
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            String filePath = uploadPath + File.separator + fileName;

            // Save image to folder
            Files.copy(imageFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

            // Save file path and food info to database
            Food foodItem = new Food(name,category,quantity,price,unit,expiryDate,filePath);
            foodServices.addNewFood(foodItem);

            return ResponseEntity.ok("Food item added successfully!");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error saving food item: " + e.getMessage());
        }

//        List <Food> foodList = foodServices.getAllFoodItems();
//        boolean found = false;
//        for(Food food : foodList){
//            if(Objects.equals(Newfood.getName(),food.getName())){
////                TODO
//                found=true;
//            }
//        }
//        if(found){
//            return ResponseEntity.ok("Not found");
//
//        }
//        foodServices.addNewFood(Newfood);
//        return ResponseEntity.ok("food sent");

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
    @DeleteMapping("/food-items/{deleteId}")
    @ResponseBody
    public ResponseEntity<?> deleteFood(@PathVariable Long deleteId){
        System.out.println("The food Id in the delete is "+deleteId);
        Boolean done = foodServices.deleteFood(deleteId);
        if(done){
            System.out.println("updated?");
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.ok("food Not");

    }
    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Path imagePath = Paths.get(uploadPath).resolve(fileName);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(imagePath))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}

