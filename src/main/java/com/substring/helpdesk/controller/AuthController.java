package com.substring.helpdesk.controller;

import com.substring.helpdesk.dto.request.LoginRequest;
import com.substring.helpdesk.dto.request.RegisterRequest;
import com.substring.helpdesk.dto.response.LoginResponse;
import com.substring.helpdesk.dto.response.RegisterResponse;
import com.substring.helpdesk.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // User registration API
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        System.out.println("Request body :"+request);
        RegisterResponse registerResponse =userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerResponse);
    }

    // User login API
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest request){

        System.out.println("Login Request:"+request);
        LoginResponse loginResponse =userService.loginUser(request);
        return ResponseEntity.ok(loginResponse);

    }



}
