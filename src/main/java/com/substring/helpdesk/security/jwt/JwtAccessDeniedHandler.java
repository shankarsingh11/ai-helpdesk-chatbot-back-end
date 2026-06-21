package com.substring.helpdesk.security.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    // User is authenticated but does not have permission.

    @Override
    public void handle
            (HttpServletRequest request,
             HttpServletResponse response,
             AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);


        Map<String,Object> body = new HashMap<>();

        body.put("timestamp", LocalTime.now());
        body.put("status",403);
        body.put("error", accessDeniedException.getMessage());
        body.put("message","Forbidden");
        body.put("path",request.getRequestURI());

        mapper.writeValue(
                response.getOutputStream(),
                body
        );

    }
}
