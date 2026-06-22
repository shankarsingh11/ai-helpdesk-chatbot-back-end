package com.substring.helpdesk.security.oauth;

import com.substring.helpdesk.security.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        CustomOAuth2User oauthUser =
                (CustomOAuth2User) authentication.getPrincipal();


        String token =
                jwtService.generateToken(
                        (UserDetails) oauthUser
                );

        String redirectUrl =
                "http://localhost:5173/oauth/success?token="
                        + URLEncoder.encode(
                        token,
                        StandardCharsets.UTF_8
                );

        log.info(
                "OAuth login successful for {}",
                oauthUser.getEmail()
        );

        response.sendRedirect(redirectUrl);
    }
}

