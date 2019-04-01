package com.example.Landing.controller;

import com.example.Landing.domain.User;
import com.example.Landing.services.UserManagementService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("api/secured")
public class LoginController {

    @Autowired
    UserManagementService userManagementService;

    @GetMapping("/login")
    public User login(@AuthenticationPrincipal User requester) {
        log.info("Login: {}", requester );
        return userManagementService.getUser(requester.getId());
    }

    @PostMapping("/login")
    public User loginPost(@AuthenticationPrincipal User requester) {
        log.info("Login: {}", requester);
        return userManagementService.getUser(requester.getId());
    }
}
