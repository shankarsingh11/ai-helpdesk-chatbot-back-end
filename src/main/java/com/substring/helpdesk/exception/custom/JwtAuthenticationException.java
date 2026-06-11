package com.substring.helpdesk.exception.custom;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message) {
        super(message);
    }
}
