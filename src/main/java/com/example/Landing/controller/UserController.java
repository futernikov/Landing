package com.example.Landing.controller;

import com.example.Landing.domain.User;
import com.example.Landing.domain.UserInDTO;
import com.example.Landing.services.MailSenderService;
import com.example.Landing.services.UserManagementService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/user/admin")
public class UserController {
    @Autowired
    UserManagementService userManagementService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    MailSenderService mailSenderService;


    @GetMapping("")
    public List<User> getAllUsers(){
        return userManagementService.getAll();
    }

    @PostMapping("")
    public User createUser(@AuthenticationPrincipal User requester, @Validated @RequestBody UserInDTO data) {
        mailSenderService.sendMessage(data.getEmail(),data.getPassword(), data.getUsername());
        return userManagementService.createUser(requester, data);
    }

    @PutMapping(value = "update/{userId}")
    public User updateUser(@AuthenticationPrincipal User requester, @Validated @RequestBody UserInDTO data,
                           @PathVariable Long userId){
        log.info("updateUser: data = {}, userId = {}", data, userId);
        return userManagementService.updateUser(requester, userId, data);
    }

    @DeleteMapping("delete/{userId}")
    public void deleteUser(@AuthenticationPrincipal User requester, @PathVariable Long userId){
        log.info("deleteUser: userId = {}", userId);
        userManagementService.deleteUser(requester, userId);
    }

}
