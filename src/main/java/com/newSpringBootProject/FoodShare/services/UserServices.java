package com.newSpringBootProject.FoodShare.services;

import com.newSpringBootProject.FoodShare.domains.User;
import com.newSpringBootProject.FoodShare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean RegisterUser(User registerUser) {
        List <User> users = getAllUsers();
        for(User user: users){
            if(Objects.equals(registerUser.getEmail(), user.getEmail()) || Objects.equals(registerUser.getPhoneNumber(),user.getPhoneNumber()) ){
                return false;
            }
        }
        userRepository.save(registerUser);
        return true;
    }
    public User getUserByName(String name){
        return userRepository.findByName(name);
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long clientId) {
       Optional<User> user =  userRepository.findById(clientId);
        return user.orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean editUser(Long Id, String name, String phoneNumber) {
        try{
            User user = getUserById(Id);
            user.setPhoneNumber(phoneNumber);
            user.setName(name);
            userRepository.save(user);
            return true;
        }catch(Exception ex){
            System.out.println(ex);
            return false;
        }
    }
}
