package com.example.Landing.controller;

import com.example.Landing.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String greeting(@AuthenticationPrincipal User user) {
        String greet;
        if(user != null) {
            greet = "Hello, " + user.getUsername();
        }
        else{
            greet  = "Hello, world";
        }
        return greet;
    }
}
