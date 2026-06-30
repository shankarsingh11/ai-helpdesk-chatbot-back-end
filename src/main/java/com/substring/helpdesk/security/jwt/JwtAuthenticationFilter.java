package com.substring.helpdesk.security.jwt;


import com.substring.helpdesk.exception.custom.JwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain)
            throws ServletException, IOException {

        log.info("JWT FILTER -> {} {}",
                request.getMethod(),
                request.getRequestURI());

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token
        try {

            String token = authHeader.substring(7);

            String email = jwtService.extractEmail(token);

            // Authenticate only if user is not already authenticated
            if (email != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtService.isTokenValid(token, userDetails)) {

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken
                                    (userDetails,
                                            null,
                                            userDetails.getAuthorities()
                                    );

                    authenticationToken.setDetails
                            (new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.debug("User authenticated successfully: {}", email);
                }

            }

        }catch(Exception ex){
            log.warn("JWT authentication failed: {}", ex.getMessage());
            throw new JwtAuthenticationException("JWT authentication failed");
        }
          filterChain.doFilter(request,response);
    }
}
