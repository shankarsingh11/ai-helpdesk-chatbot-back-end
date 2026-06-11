package com.substring.helpdesk.security;


import com.substring.helpdesk.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


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
                                     "/api/v1/auth/login",
                                     "/api/v1/helpdesk"
                             ).permitAll()
                                     .anyRequest()
                                     .authenticated())
                     .authenticationProvider(authenticationProvider());

             return http.build();
     }

     // DaoAuthenticationProvider
     @Bean
     public AuthenticationProvider authenticationProvider(){
     DaoAuthenticationProvider provider =
             new DaoAuthenticationProvider();
         provider.setUserDetailsService(userDetailsService);
         provider.setPasswordEncoder(passwordEncoder);
         return provider;
     }

    // AuthenticationManager
   @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
       return configuration.getAuthenticationManager();
    }

}
