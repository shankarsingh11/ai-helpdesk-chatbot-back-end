package com.substring.helpdesk.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

 private final UserDetailsService userDetailsService;


 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

             http
                     . csrf(csrf-> csrf.disable())
                     .sessionManagement(session->
                             session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                     .authorizeHttpRequests(auth->
                             auth.requestMatchers(
                                     // public api
                                     "/api/v1/auth/register",
                                     "/api/v1/auth/login"
                             ).permitAll()
                                     .anyRequest()
                                     .authenticated());

             return http.build();
     }

}
