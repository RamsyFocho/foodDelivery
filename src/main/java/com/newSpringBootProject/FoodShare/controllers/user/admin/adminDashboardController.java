package com.newSpringBootProject.FoodShare.controllers.user.admin;



//import com.example.food.service.DashboardService;
import com.newSpringBootProject.FoodShare.services.adminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class adminDashboardController {

    @Autowired
    private adminDashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        return ResponseEntity.ok(dashboardService.getDashboardStatistics());
    }

    @GetMapping("/sales")
    public ResponseEntity<Map<String, Object>> getSalesData(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        return ResponseEntity.ok(dashboardService.getSalesData(year, month));
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getCategoryData() {
        return ResponseEntity.ok(dashboardService.getCategoryData());
    }
}
