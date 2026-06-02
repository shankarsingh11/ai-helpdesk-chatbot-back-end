package com.substring.helpdesk.service.impl;

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
@Data
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String usernameOremail)
            throws UsernameNotFoundException {

        log.info("Load user by username: {}", usernameOremail);

        User user = findUserDetails(usernameOremail);

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.EMPTY_LIST)//user has no roles and no permissions.
                .build();
    }



    // find userdetails method
    private  User  findUserDetails(String usernameOremail){
        User user = userRepo
                .findByUsernameOrEmail
                        (usernameOremail, usernameOremail)
                .orElseThrow(() -> {
                    log.info("User not found:{}", usernameOremail);
                    return new UserNotFoundException("User not found" + usernameOremail);
                });
        return  user;
    }

}