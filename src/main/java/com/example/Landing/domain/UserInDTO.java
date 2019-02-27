package com.example.Landing.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
    @Data
    @NoArgsConstructor
    public class UserInDTO {

        @NotNull
        private String username;
        @NotNull
        private String password;
        @NotNull
        private Role role;
        @NotNull
        private String email;
        private String firstName;
        private String lastName;
}
