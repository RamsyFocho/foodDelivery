package com.newSpringBootProject.FoodShare.controllers.user;

import com.newSpringBootProject.FoodShare.domains.User;
import com.newSpringBootProject.FoodShare.services.UserServices;
import com.newSpringBootProject.FoodShare.webdomains.LoginRequest;
import com.newSpringBootProject.FoodShare.webdomains.RegisterUser;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/auth")
@Data
public class AuthController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?>Login(@RequestBody LoginRequest loginRequest,HttpSession session){
        System.out.println(loginRequest.getEmail());
        System.out.println(loginRequest.getPassword());
        User user = userServices.getUserByEmail(loginRequest.getEmail());
        if(user == null){
            return ResponseEntity.ok(Map.of("status","failed","message","email" ));
        }else if(Objects.equals(user.getPassword(), loginRequest.getPassword())){
            if(Objects.equals(user.getRole(), "admin")){
                session.setAttribute("adminId",user.getId());
            }else{
                session.setAttribute("clientId",user.getId());
            }
            session.setAttribute("role",user.getRole());
            return ResponseEntity.ok(Map.of("status","success","userId",user.getId(),"role",user.getRole() ));

        }else{
//            TODO : incorrect password
            return ResponseEntity.ok(Map.of("status","failed","message","password" ));

        }

    }
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody RegisterUser editUser) {
        System.out.println(id);
        System.out.println(editUser.getPhoneNumber());
        System.out.println(editUser.getName());
        if(id == null || editUser.getName() == null || editUser.getPhoneNumber() == null){
            return ResponseEntity.ok(Map.of("status","failed","message","The values are null"));
        }
        boolean done = userServices.editUser(id,editUser.getName(), editUser.getPhoneNumber());
//        model.addAttribute("user", userServices.getUserById(id).orElse(null));
        if(done){
            return ResponseEntity.ok(Map.of("status","success"));
        }else{
            return ResponseEntity.ok(Map.of("status","failed","message", "failed updating the info"));
        }
    }
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> Register(@RequestBody RegisterUser registerUser, HttpSession session){
        System.out.println(registerUser.getEmail());
        System.out.println(registerUser.getPassword());
        System.out.println(registerUser.getPhoneNumber());
        User newUser = new User();
        newUser.setName(registerUser.getName());
        newUser.setEmail(registerUser.getEmail());
        newUser.setPassword(registerUser.getPassword());
        newUser.setRole("client");
        newUser.setPhoneNumber(registerUser.getPhoneNumber());
        newUser.setCreatedDate(LocalDate.now());

        boolean done = userServices.RegisterUser(newUser);
        if(done){
            session.setAttribute("clientId",newUser.getId());
            session.setAttribute("CusphoneNumber",registerUser.getPhoneNumber());
            System.out.println(newUser.getId());
            return ResponseEntity.ok(Map.of("status","success","clientId",newUser.getId()));
        }
        return ResponseEntity.ok(Map.of("status","failed"));
    }
}

