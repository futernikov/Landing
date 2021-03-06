package com.example.Landing.services;


import com.example.Landing.domain.User;
import com.example.Landing.domain.UserInDTO;
import com.example.Landing.exeption.ErrorCodes;
import com.example.Landing.exeption.RestException;
import com.example.Landing.repo.UserDetailsRepo;
import com.example.Landing.validators.UserManagementValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.example.Landing.domain.Role.ROLE_ADMIN;

@Service
@Log4j2
public class UserManagementService {
    @Autowired
    UserDetailsRepo userDetailsRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserManagementValidator userManagementValidator;
    @Autowired
    UserManagementService userManagementService;


    @PostConstruct
    public void initUser(){
        long count = userDetailsRepo.count();
        if(count == 0){
            UserInDTO data = new UserInDTO("admin", "admin", ROLE_ADMIN,
                    "lanec120898@gmail.com", "Eugene", "Lanets");

            userManagementService.createUser(null, data);
        }
    }


    public User createUser(User requester, UserInDTO data){
        data.setPassword(passwordEncoder.encode(data.getPassword()));
        return userDetailsRepo.save(User.of(data));
    }

    public User getUser(Long id) {
        return userDetailsRepo.findById(id).orElseThrow(()-> new RestException(ErrorCodes.USER_NOT_EXIST));
    }

    public User updateUser(User requester, Long userId, UserInDTO data){
        log.debug("updateUser: requester = {}, userId = {}, data = {}", requester, userId, data);
        User fromDB = userManagementValidator.validateUpdateUserAndReturn(requester, userId, data);
        return userDetailsRepo.save(User.merge(fromDB,data));
    }

    public void deleteUser(User requester, Long userId) {
        log.debug("deleteUser: requester = {}, userId = {}", requester, userId);
        userManagementValidator.validateDeleteUser(requester, userId);
        userDetailsRepo.deleteById(userId);
    }

    public List<User> getAll() {
        return userDetailsRepo.findAll();
    }
}
