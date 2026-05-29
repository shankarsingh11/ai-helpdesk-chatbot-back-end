package com.substring.helpdesk.controller;

import com.substring.helpdesk.dto.request.UserRequestDTO;
import com.substring.helpdesk.service.UserService;
import com.substring.helpdesk.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok(userService.saveUser(userRequestDTO));
    }

    @GetMapping("/login")
    public ResponseEntity<String> login (@RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok("");
    }

}
