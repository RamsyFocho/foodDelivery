package com.newSpringBootProject.FoodShare.services;

import com.newSpringBootProject.FoodShare.domains.Food;
import com.newSpringBootProject.FoodShare.domains.Order;
import com.newSpringBootProject.FoodShare.repository.FoodRepository;
import com.newSpringBootProject.FoodShare.repository.OrderRepository;
import com.newSpringBootProject.FoodShare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class adminDashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRepository;



    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();
        LocalDate now = LocalDate.now();
        LocalDate monthAgo = now.minusMonths(1);

        // Calculate current month stats
        List<Order> currentMonthOrders = orderRepository.findByOrderDateBetween(monthAgo, now);
        List<Order> previousMonthOrders = orderRepository.findByOrderDateBetween(
                monthAgo.minusMonths(1), monthAgo);

        // Calculate totals
        int totalOrders = currentMonthOrders.size();
        double totalRevenue = currentMonthOrders.stream()
                .mapToDouble(Order::getTotal)
                .sum();

        // Calculate previous month totals for growth
        int prevMonthOrders = previousMonthOrders.size();
        double prevMonthRevenue = previousMonthOrders.stream()
                .mapToDouble(Order::getTotal)
                .sum();

        // Calculate growth percentages
        double orderGrowth = calculateGrowth(prevMonthOrders, totalOrders);
        double revenueGrowth = calculateGrowth(prevMonthRevenue, totalRevenue);

        // Get active users (users who placed orders in the last month)
        long activeUsers = currentMonthOrders.stream()
                .map(Order::getUser)
                .distinct()
                .count();

        // Get low stock items (items with quantity less than 10)
        long lowStockItems = foodRepository.countByQuantityLessThan(10);

        stats.put("totalOrders", totalOrders);
        stats.put("totalRevenue", totalRevenue);
        stats.put("activeUsers", activeUsers);
        stats.put("lowStockItems", lowStockItems);
        stats.put("orderGrowth", orderGrowth);
        stats.put("revenueGrowth", revenueGrowth);
        stats.put("userGrowth", calculateUserGrowth());

        return stats;
    }

    public Map<String, Object> getSalesData(Integer year, Integer month) {
        int targetYear = year != null ? year : LocalDate.now().getYear();
        Map<String, Object> result = new HashMap<>();

        // Get start and end dates
        LocalDate startDate = LocalDate.of(targetYear, 1, 1);
        LocalDate endDate = LocalDate.of(targetYear, 12, 31);

        // Get all orders for the year
        List<Order> orders = orderRepository.findByOrderDateBetween(startDate, endDate);

        // Group orders by month
        Map<String, Double> monthlySales = new LinkedHashMap<>();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        // Initialize all months with 0
        Arrays.asList(months).forEach(m -> monthlySales.put(m, 0.0));

        // Calculate sales for each month
        for (Order order : orders) {
            // Get month index (1-12) and convert to array index (0-11)
            int monthIndex = order.getOrderDate().getMonthValue() - 1;
            // Use the month name from our array for consistency
            String monthName = months[monthIndex];
            monthlySales.merge(monthName, order.getTotal(), Double::sum);
        }

        // Create a map structure that matches the frontend expectations
        Map<String, Object> salesData = new HashMap<>();
        salesData.put("labels", new ArrayList<>(monthlySales.keySet()));
        salesData.put("values", new ArrayList<>(monthlySales.values()));

        result.put("sales", salesData);
        return result;
    }

    public Map<String, Object> getCategoryData() {
        Food food = new Food();
        Map<String, Object> result = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        // Group foods by category and sum their quantities
        Map<String, Integer> categoryQuantities = foodRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Food::getCategory,
                        Collectors.summingInt(Food::getQuantity)
                ));

        labels.addAll(categoryQuantities.keySet());
        data.addAll(categoryQuantities.values());

        result.put("labels", labels);
        result.put("values", data);
        return result;
    }

    private double calculateGrowth(double previous, double current) {
        if (previous == 0) return 100.0;
        return ((current - previous) / previous) * 100.0;
    }

    private double calculateUserGrowth() {
        LocalDate now = LocalDate.now();
        LocalDate monthAgo = now.minusMonths(1);
        LocalDate twoMonthsAgo = monthAgo.minusMonths(1);

        long currentUsers = userRepository.countByCreatedDateBetween(monthAgo, now);
        long previousUsers = userRepository.countByCreatedDateBetween(twoMonthsAgo, monthAgo);

        return calculateGrowth(previousUsers, currentUsers);
    }
}
