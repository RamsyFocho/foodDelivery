package com.newSpringBootProject.FoodShare.controllers;

import com.newSpringBootProject.FoodShare.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public String sendSms(@RequestParam String phoneNumber, @RequestParam String message) {
        smsService.sendSms(phoneNumber, message);
        return "SMS sent successfully!";
    }
}
