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
//            sou
            return ResponseEntity.ok(Map.of("status","failed","message","email" ));

        }else if(Objects.equals(user.getPassword(), loginRequest.getPassword())){
            if(Objects.equals(user.getRole(), "admin")){
                session.setAttribute("adminId",user.getId());
            }else{
                session.setAttribute("clientId",user.getId());
            }

            return ResponseEntity.ok(Map.of("status","success","userId",user.getId(),"role",user.getRole() ));

        }else{
//            TODO : incorrect password
            return ResponseEntity.ok(Map.of("status","failed","message","password" ));

        }

    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> Register(@RequestBody RegisterUser registerUser, HttpSession session){
        System.out.println(registerUser.getEmail());
        System.out.println(registerUser.getPassword());
        User newUser = new User();
        newUser.setName(registerUser.getName());
        newUser.setEmail(registerUser.getEmail());
        newUser.setPassword(registerUser.getPassword());
        newUser.setRole("client");
        newUser.setCreatedDate(LocalDate.now());

        boolean done = userServices.RegisterUser(newUser);
        if(done){
            session.setAttribute("clientId",newUser.getId());
            System.out.println(newUser.getId());
            return ResponseEntity.ok(Map.of("status","success","clientId",newUser.getId()));
        }
        return ResponseEntity.ok(Map.of("status","failed"));
    }
}

