package com.example.Landing.services;


import com.example.Landing.domain.User;
import com.example.Landing.domain.UserInDTO;
import com.example.Landing.exeption.ErrorCodes;
import com.example.Landing.exeption.RestException;
import com.example.Landing.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagementService {
    @Autowired
    UserDetailsRepo userDetailsRepo;

    public User cerateUser(User requester, UserInDTO data){
        return userDetailsRepo.save(User.of(data));
    }

    public User getUser(String id) {
        return userDetailsRepo.findById(id).orElseThrow(()-> new RestException(ErrorCodes.USER_NOT_EXIST));
    }

}
