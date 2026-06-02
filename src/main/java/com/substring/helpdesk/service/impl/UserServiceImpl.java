package com.substring.helpdesk.service.impl;

import com.substring.helpdesk.dto.request.LoginRequestDTO;
import com.substring.helpdesk.dto.request.RegisterRequestDTO;
import com.substring.helpdesk.dto.response.LoginResponseDTO;
import com.substring.helpdesk.dto.response.RegisterResponseDTO;
import com.substring.helpdesk.entity.User;
import com.substring.helpdesk.exception.custom.UserAlreadyExistsException;
import com.substring.helpdesk.exception.custom.UserNotFoundException;
import com.substring.helpdesk.repository.UserRepo;

import com.substring.helpdesk.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    // user registration logic
    @Override
    public RegisterResponseDTO createUser(RegisterRequestDTO registerRequestDTO) {

        //Validate Duplicate user properties

        // check existing username or email
        if(userRepo.existsByEmail(registerRequestDTO.getEmail())) {
            log.info("register email:{}",registerRequestDTO.getEmail());
            throw new UserAlreadyExistsException("Email already registered try another email id");
        }

       // check exists username
        if (userRepo.existsByUsername(registerRequestDTO.getUsername())){
            log.info("Username:{}",registerRequestDTO.getUsername());
            throw new UserAlreadyExistsException("Username already registered try another username");
        }

       /* // check exists password
        if (userRepo.existsByPassword(registerRequestDTO.getPassword())){
            log.info("Password:{}",registerRequestDTO.getPassword());
            throw new UserAlreadyExistsException("duplicate Password please try another password");
        }*/

        // User Object created
        User user = new User();
        user.setName(registerRequestDTO.getName());
        user.setUsername(registerRequestDTO.getUsername().trim());
        user.setEmail(registerRequestDTO.getEmail().trim());
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        //save  user details information
        User savedUser = userRepo.save(user);

        //logs printed
         log.info("Name:{}",savedUser.getName());
         log.info("user_id:{}",savedUser.getUsername());
         log.info("email:{}",savedUser.getEmail());

        return RegisterResponseDTO.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .message("User registered successfully")
                .build();
    }

    // User login logic
    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsernameOrEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // fetch full user from DB
        User user = userRepo.findByUsernameOrEmail(
                        userDetails.getUsername(),
                        userDetails.getUsername()
                )
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        log.info("User logged in successfully. Username: {}", user.getUsername());

        return LoginResponseDTO.builder()
                .message("User Login Successfully")
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

}
