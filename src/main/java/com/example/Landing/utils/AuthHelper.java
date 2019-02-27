package com.example.Landing.utils;


import com.example.Landing.domain.Role;

import java.util.List;

public class AuthHelper {
    private AuthHelper() {
    }

    public static String[] convertToAuthorities(List<Role> grantedAuthorities) {
        return grantedAuthorities.stream().map(Enum::name).toArray(String[]::new);
    }

    public static String[] convertToAuthorities(Role grantedAuthorities) {
        return new String[] {grantedAuthorities.name()};
    }
}