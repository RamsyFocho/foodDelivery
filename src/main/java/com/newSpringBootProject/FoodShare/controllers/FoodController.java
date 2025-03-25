package com.newSpringBootProject.FoodShare.controllers;

import com.newSpringBootProject.FoodShare.domains.Food;
import com.newSpringBootProject.FoodShare.repository.FoodRepository;
import com.newSpringBootProject.FoodShare.services.FoodServices;
import com.newSpringBootProject.FoodShare.webdomains.MetaFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.*;

@RestController
@RequestMapping("/foodApi")
public class FoodController {
    @Autowired
    private FoodServices foodServices;
//    @Value("${upload.path}") // Set upload folder path in application.properties
//    private String uploadPath;

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
            // Define upload directory
            String uploadDir = "src/main/resources/static/uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs(); // Create directory if not exists
            }

            // Generate unique filename
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // Save image to folder
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Store only relative path in DB (for easy retrieval)
            String imageUrl = "/uploads/" + fileName;
            // Save file path and food info to database
            Food foodItem = new Food(name,category,quantity,price,unit,expiryDate,imageUrl);
            foodServices.addNewFood(foodItem);

            return ResponseEntity.ok("Food item added successfully!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Error saving food item: " + e.getMessage());
        }


    }
    @PutMapping("/food-items/{editingId}")
    @ResponseBody
    public ResponseEntity<?> updateFood(
            @PathVariable Long editingId,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("quantity") int quantity,
            @RequestParam("price") double price,
            @RequestParam("unit") String unit,
            @RequestParam("expiryDate") LocalDate expiryDate,
            @RequestParam(value = "imageField", required = false) MultipartFile imageFile) {

        try {
            // Fetch the existing food item
            Food existingFood = foodServices.getFoodById(editingId);
            if (existingFood == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Food item not found");
            }

            // Set new values from the form
            existingFood.setName(name);
            existingFood.setCategory(category);
            existingFood.setQuantity(quantity);
            existingFood.setPrice(price);
            existingFood.setUnit(unit);
            existingFood.setExpiryDate(expiryDate);

            // Handle image update (if a new image is uploaded)
            if (imageFile != null && !imageFile.isEmpty()) {
                // Define upload directory
                String uploadDir = "src/main/resources/static/uploads/";
                File directory = new File(uploadDir);
                if (!directory.exists()) {
                    directory.mkdirs(); // Create directory if not exists
                }

                // Generate unique filename
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);

                // Save new image
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Update image path in DB (keeping only relative path)
                String newImageUrl = "/uploads/" + fileName;
                existingFood.setImagePath(newImageUrl);
            }

            // Save the updated food item
            foodServices.updateFoodById(editingId, existingFood);

            // Return JSON response with updated details
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Food item updated successfully!");
            response.put("foodItem", existingFood);
            response.put("imageUrl", "http://localhost:8080" + existingFood.getImagePath()); // Full URL

            return ResponseEntity.ok("Food updated");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error updating food item: " + e.getMessage()));
        }
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
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        System.out.println("---------------Getting Image----------");
        Path imagePath = Paths.get(imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

}

