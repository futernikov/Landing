package com.example.Landing.controller;

import com.example.Landing.domain.User;
import com.example.Landing.domain.UserInDTO;
import com.example.Landing.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserManagementService userManagementService;

    @GetMapping("/login")
    public User login(@AuthenticationPrincipal User requester) {
        return userManagementService.getUser(requester.getId());
    }

    @PostMapping("/login")
    public User loginPost(@AuthenticationPrincipal User requester) {
        return userManagementService.getUser(requester.getId());
    }

    @PostMapping("")
    public User createUser(@AuthenticationPrincipal User requester, @Validated @RequestBody UserInDTO data) {
        return userManagementService.cerateUser(requester, data);
    }

}
