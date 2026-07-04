package com.substring.helpdesk.security.config;


import com.substring.helpdesk.security.jwt.JwtAccessDeniedHandler;
import com.substring.helpdesk.security.jwt.JwtAuthenticationEntryPoint;
import com.substring.helpdesk.security.jwt.JwtAuthenticationFilter;
import com.substring.helpdesk.security.oauth.CustomOAuth2UserService;
import com.substring.helpdesk.security.oauth.OAuth2AuthenticationFailureHandler;
import com.substring.helpdesk.security.oauth.OAuth2AuthenticationSuccessHandler;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;


 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

     log.info("========== SECURITY CONFIG LOADED ==========");

             http
                     . csrf(csrf-> csrf.disable())
                     .exceptionHandling( ex ->
                                ex
                                     .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                        .accessDeniedHandler(jwtAccessDeniedHandler)
                     )
                     .sessionManagement(session->
                             session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                     .authorizeHttpRequests(auth->
                             auth
<<<<<<< HEAD
                                     // public auth api
                                     .requestMatchers(
                                           // public api
=======
                                     .requestMatchers(
                                     // public api
>>>>>>> 7ba51ed (swagger-ui updated)
                                             "/api/v1/auth/**",
                                             "/oauth2/**",
                                             "/login/oauth2/**"
                             ).permitAll()
<<<<<<< HEAD
                                     //swagger
                                     .requestMatchers(
                                             "/swagger-ui/**",
                                             "/swagger-ui.html",
                                             "/api-docs/**",
                                             "/v3/api-docs/**"
=======
                                     .requestMatchers(
                                             "/swagger-ui.html",
                                             "/swagger-ui/**",
                                             "/api-docs/**",
                                             "/v3/api-docs/**",
                                             "/webjars/**"
>>>>>>> 7ba51ed (swagger-ui updated)
                                     ).permitAll()
                                     .anyRequest().authenticated())

                     .oauth2Login(oauth ->
                                      oauth.userInfoEndpoint(userInfo-> userInfo.userService(customOAuth2UserService))
                                              .successHandler(oAuth2AuthenticationSuccessHandler)
                                              .failureHandler(oAuth2AuthenticationFailureHandler)
                     )

                     .addFilterBefore(
                             jwtAuthenticationFilter,
                             UsernamePasswordAuthenticationFilter.class
                     )
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
