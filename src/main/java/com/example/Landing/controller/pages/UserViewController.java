package com.example.Landing.controller.pages;

import com.example.Landing.domain.User;
import com.example.Landing.domain.UserInDTO;
import com.example.Landing.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("view/users")
public class UserViewController {
    @Autowired
    UserManagementService userManagementService;

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("users", userManagementService.getAll());
        return "users";
    }

    @PostMapping("/add")
    public String addUser(Model model, @RequestBody UserInDTO data, @AuthenticationPrincipal User requester){
        model.addAttribute(userManagementService.createUser(requester, data));
        return "users";
    }

    @GetMapping("/add")
    public String getAddView(Model model){
        return "add";
    }
}
