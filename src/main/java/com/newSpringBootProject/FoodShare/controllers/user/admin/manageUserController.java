package com.newSpringBootProject.FoodShare.controllers.user.admin;

import com.newSpringBootProject.FoodShare.domains.User;
import com.newSpringBootProject.FoodShare.services.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class manageUserController {
    @Autowired
    private UserServices userService;

    @GetMapping
    public String listUsers(Model model, HttpSession session) {
        if(session.getAttribute("adminId") == null){
            return "redirect:/loginPage";
        }
        model.addAttribute("users", userService.getAllUsers());
           return  "/userManagement.html";
    }

    @GetMapping("/new")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/form";
    }

//    @PostMapping("/save")
//    public String saveUser(@ModelAttribute User user) {
//
//        userService.RegisterUser(user);
//        return "redirect:/users";
//    }

//    @GetMapping("/edit/{id}")
//    public String editUser(@PathVariable Long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id).orElse(null));
//        return "user/form";
//    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}

