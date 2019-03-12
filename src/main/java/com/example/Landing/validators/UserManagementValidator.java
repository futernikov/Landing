package com.example.Landing.validators;

import com.example.Landing.domain.Role;
import com.example.Landing.domain.User;
import com.example.Landing.domain.UserInDTO;
import com.example.Landing.exeption.ErrorCodes;
import com.example.Landing.exeption.RestException;
import com.example.Landing.repo.UserDetailsRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;


@Log4j2
@Service
public class UserManagementValidator {
    @Autowired
    private UserDetailsRepo userDetailsRepo;

    public User validateUpdateUserAndReturn(User requester, Long userId, UserInDTO data) {
        if (Objects.isNull(userId) || userId == null) {
            throw new RestException(ErrorCodes.USER_WRONG_ID);
        }
        if (Objects.isNull(data)) {
            throw new RestException(ErrorCodes.USER_EMPTY_DATA);
        }
        // TODO validate data
        User fromDb = userDetailsRepo.findById(userId)
                .orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
        if (requester.getId().equals(userId) && Arrays.asList(fromDb.getGrantedAuthorities())
                .contains(Role.ROLE_ADMIN.name()) && !data.getRole().equals(Role.ROLE_ADMIN)) {
            throw new RestException(ErrorCodes.USER_WRONG_ROLES);
        }
        return fromDb;
    }

    public void validateDeleteUser(User requester, Long userId) {
        if (Objects.isNull(userId) || userId == null) {
            throw new RestException(ErrorCodes.USER_WRONG_ID);
        }
        if (requester.getId().equals(userId)) {
            throw new RestException(ErrorCodes.USER_CANT_DELETE_HIMSELF);
        }
        userDetailsRepo.findById(userId).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
    }
}


