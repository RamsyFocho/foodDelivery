package com.newSpringBootProject.FoodShare.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.exception.ApiException;

@Service
public class SmsService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;


    public void sendSms(String toPhoneNumber, String messageBody) {
        System.out.println("------------------------------sending SMS-----------------------------");

        try {
            if (accountSid == null || authToken == null || fromPhoneNumber == null) {
                throw new IllegalStateException("Twilio credentials are not properly configured.");
            }

            if (toPhoneNumber == null || messageBody == null) {
                throw new IllegalArgumentException("Phone number or message body cannot be null.");
            }

            Twilio.init(accountSid, authToken);

            Message message = Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(fromPhoneNumber),
                    messageBody
            ).create();

            System.out.println("SMS sent successfully! Message SID: " + message.getSid());

        } catch (ApiException e) {
            System.err.println("Twilio API Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
