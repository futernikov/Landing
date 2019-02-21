package com.example.Landing.controller;

import com.example.Landing.domain.User;
import com.example.Landing.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public MainController(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    @GetMapping("{id}")
    public String greeting(@PathVariable("id") User user) {
        String greet;
        if(user != null) {
            greet = "Hello, " + user.getName();
        }
        else{
            greet  = "Hello, world";
        }
        return greet;
    }
}
