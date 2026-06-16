package com.substring.helpdesk.security;

import com.substring.helpdesk.entity.User;
import com.substring.helpdesk.exception.custom.UserNotFoundException;
import com.substring.helpdesk.repository.UserRepo;
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
                .password(user.getPassword())
                .authorities(Collections.emptyList())//user has no roles and no permissions.
                .build();
    }



    // find userdetails method
    private  User  findUserDetails(String email){
        User user = userRepo
                .findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found:{}", email);
                    return new UserNotFoundException("User not found" + email);
                });
        return  user;
    }

}