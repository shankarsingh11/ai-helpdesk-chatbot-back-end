package com.substring.helpdesk.service.common;

import com.substring.helpdesk.entity.User;
import com.substring.helpdesk.exception.custom.UserNotFoundException;
import com.substring.helpdesk.repository.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {

        log.info("Load user by username/Email: {}", usernameOrEmail);

        User user = findUserDetails(usernameOrEmail);

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.emptyList())//user has no roles and no permissions.
                .build();
    }



    // find userdetails method
    private  User  findUserDetails(String usernameOrEmail){
        User user = userRepo
                .findByUsernameIgnoreCaseOrEmailIgnoreCase
                        (usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> {
                    log.warn("User not found:{}", usernameOrEmail);
                    return new UserNotFoundException("User not found" + usernameOrEmail);
                });
        return  user;
    }

}