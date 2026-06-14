package com.substring.helpdesk.controller;

import com.substring.helpdesk.dto.request.LoginRequestDTO;
import com.substring.helpdesk.dto.request.RegisterRequestDTO;
import com.substring.helpdesk.dto.response.LoginResponseDTO;
import com.substring.helpdesk.dto.response.RegisterResponseDTO;
import com.substring.helpdesk.service.auth.UserService;
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
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request){
        System.out.println("Request body :"+request);
        RegisterResponseDTO registerResponseDTO=userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerResponseDTO);
    }

    // User login API
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@Valid @RequestBody LoginRequestDTO request){

        System.out.println("Login Request:"+request);
        LoginResponseDTO loginResponseDTO=userService.loginUser(request);
        return ResponseEntity.ok(loginResponseDTO);

    }



}
