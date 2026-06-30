package com.substring.helpdesk.controller;

import com.substring.helpdesk.dto.request.GithubLoginRequest;
import com.substring.helpdesk.dto.request.GoogleLoginRequest;
import com.substring.helpdesk.dto.request.LoginRequest;
import com.substring.helpdesk.dto.request.RegisterRequest;
import com.substring.helpdesk.dto.response.LoginResponse;
import com.substring.helpdesk.dto.response.OAuthLoginResponse;
import com.substring.helpdesk.dto.response.RegisterResponse;
import com.substring.helpdesk.service.oauth.OAuthAuthenticationService;
import com.substring.helpdesk.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final OAuthAuthenticationService oauthAuthenticationService;

    // Controller test API
    @GetMapping("/test")
    public String test() {
        System.out.println("TEST CONTROLLER HIT");
        return "SUCCESS";
    }

    // User registration API
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        log.info("Received user registration request: POST /api/v1/auth/register");
        RegisterResponse response =userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // User login API
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest request){
        log.info("Received user login request: POST /api/v1/auth/login");
        LoginResponse response =userService.loginUser(request);
        return ResponseEntity.ok(response);

    }

    // User GoogleLogin API
    @PostMapping("/google")
    public ResponseEntity<OAuthLoginResponse> googleLogin(@Valid @RequestBody GoogleLoginRequest request) throws GeneralSecurityException, IOException {
        System.out.println("========================");
        System.out.println("GOOGLE API HIT");
        System.out.println("========================");

        log.info("Received Google login request: POST /helpdesk/api/v1/auth/google");
        OAuthLoginResponse response = oauthAuthenticationService.googleLogin(request);
        return ResponseEntity.ok(response);
    }

    // USer GithubLogin API
    @PostMapping("/github")
    public ResponseEntity<OAuthLoginResponse> githubLogin(@Valid @RequestBody GithubLoginRequest request){


        log.info("Received GitHub login request: POST /helpdesk/api/v1/auth/github");
        OAuthLoginResponse response = oauthAuthenticationService.githubLogin(request);
        return ResponseEntity.ok(response);
    }



}
