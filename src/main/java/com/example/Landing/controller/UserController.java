package com.example.Landing.controller;

import com.example.Landing.domain.User;
import com.example.Landing.domain.UserInDTO;
import com.example.Landing.repo.UserDetailsRepo;
import com.example.Landing.services.UserManagementService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import static com.example.Landing.domain.Role.ROLE_ADMIN;

@Log4j2
@RestController
@RequestMapping("/user/admin")
public class UserController {
    @Autowired
    UserManagementService userManagementService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsRepo userDetailsRepo;

    @PostConstruct
    public void initUser(){
        long count = userDetailsRepo.count();
        if(count == 0){
            UserInDTO data = new UserInDTO("admin", "admin", ROLE_ADMIN,
                    "email", "Eugene", "Lanets");

            userManagementService.createUser(null, data);
        }
    }

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

    @PostMapping("")
    public User createUser(@AuthenticationPrincipal User requester, @Validated @RequestBody UserInDTO data) {
        return userManagementService.createUser(requester, data);
    }

}
