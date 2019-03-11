package com.example.Landing.services;

import com.example.Landing.repo.UserDetailsRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Log4j2
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.debug("loadUserByUsername: {}", login);
        return userDetailsRepo.findFirstByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
    }
}