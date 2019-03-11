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

        public UserInDTO(@NotNull String username, @NotNull String password, @NotNull Role role, @NotNull String email, String firstName, String lastName) {
            this.username = username;
            this.password = password;
            this.role = role;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        private String firstName;
        private String lastName;

    }
