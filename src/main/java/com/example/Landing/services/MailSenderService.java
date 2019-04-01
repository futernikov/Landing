package com.example.Landing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendMessage(String email, String pass, String username){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Account");
        message.setText("\n" +
                "Your account has been successfully created."+"\n"+
                "Login:"+ username +"\n"+
                "Password:"+ pass);

        this.emailSender.send(message);
    }
}
