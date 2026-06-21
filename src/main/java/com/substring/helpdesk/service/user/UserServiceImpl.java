package com.substring.helpdesk.service.user;

import com.substring.helpdesk.dto.request.LoginRequest;
import com.substring.helpdesk.dto.request.RegisterRequest;
import com.substring.helpdesk.dto.response.LoginResponse;
import com.substring.helpdesk.dto.response.RegisterResponse;
import com.substring.helpdesk.entity.User;
import com.substring.helpdesk.exception.custom.UserAlreadyExistsException;
import com.substring.helpdesk.exception.custom.UserNotFoundException;
import com.substring.helpdesk.repository.UserRepo;

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
    public RegisterResponse createUser(RegisterRequest registerRequest) {

        //Validate Duplicate user properties

        // check existing email
        if(userRepo.existsByEmailIgnoreCase(registerRequest.getEmail())) {
            log.info("register email:{}", registerRequest.getEmail());
            throw new UserAlreadyExistsException("Email already registered try another email id");
        }

       /* // check exists password
        if (userRepo.existsByPassword(registerRequestDTO.getPassword())){
            log.info("Password:{}",registerRequestDTO.getPassword());
            throw new UserAlreadyExistsException("duplicate Password please try another password");
        }*/

        // User Object created
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail().trim());
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        //save  user details information
        User savedUser = userRepo.save(user);

        //logs printed
         log.info("Name:{}",savedUser.getName());
         log.info("email:{}",savedUser.getEmail());

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .message("User registered successfully")
                .build();
    }

    // User login logic
    @Override
    public LoginResponse loginUser(LoginRequest loginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // fetch full user from DB
        User user = userRepo.findByEmail(
                        userDetails.getUsername()
                )
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        log.info("User logged in successfully : {}", user.getEmail());

        return LoginResponse.builder()
                .message("User Login Successfully")
                .build();
    }

}
