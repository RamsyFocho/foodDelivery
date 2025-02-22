package com.newSpringBootProject.FoodShare.services;

import com.newSpringBootProject.FoodShare.domains.FoodOrder;
import com.newSpringBootProject.FoodShare.domains.Order;
import com.newSpringBootProject.FoodShare.domains.User;
import com.newSpringBootProject.FoodShare.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodOrderService {

    @Autowired
    private FoodOrderRepository foodOrderRepository;
    // order food and provide order details
    public boolean addFoodOrder(FoodOrder foodOrder){
        try {
            foodOrderRepository.save(foodOrder);
            return true;
        } catch (Exception e) {
            return false;
//            throw new RuntimeException(e);
        }

    }

}
