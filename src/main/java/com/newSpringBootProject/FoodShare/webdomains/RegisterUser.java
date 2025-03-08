package com.newSpringBootProject.FoodShare.webdomains;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegisterUser {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;

    public RegisterUser(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}

