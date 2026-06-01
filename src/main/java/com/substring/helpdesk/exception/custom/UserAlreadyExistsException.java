package com.substring.helpdesk.exception.custom;

import org.springframework.web.bind.annotation.RestControllerAdvice;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message){
        super(message);
    }

}
