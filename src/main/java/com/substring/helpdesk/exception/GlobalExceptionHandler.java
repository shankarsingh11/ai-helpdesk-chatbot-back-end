package com.substring.helpdesk.exception;

import com.substring.helpdesk.dto.response.AIServiceErrorResponse;
import com.substring.helpdesk.dto.response.ErrorResponse;
import com.substring.helpdesk.exception.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // custom exception handler

    // UserAlreadyExistsException
    @ExceptionHandler(UserAlreadyExistsException.class)
public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex,
                                                             HttpServletRequest request){
       log.error("UserAlready exist error:" ,ex);

        // create error response object
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error("USER_ALREADY_EXISTS")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);

    }

    // UserNotFoundException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex,
                                                            HttpServletRequest request) {
      log.error("UserNotFound exception :",ex);

        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error("USER_NOT_FOUND")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

// ResourceNotFoundException Handler
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex,
                                                                HttpServletRequest request){

        log.error("Resource Not found error:",ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getMessage())
                .message("RESOURCE_NOT_FOUND")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity
                           .status(HttpStatus.NOT_FOUND)
                            .body(errorResponse);

    }

    // AIServiceValidatorException Handler
    @ExceptionHandler(AIServiceValidatorException.class)
    public ResponseEntity<ErrorResponse> handlerAIServiceValidatorException(AIServiceValidatorException ex, HttpServletRequest request){

        log.error("AI validation failed", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("VALIDATION_ERROR")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }

    // AIServiceResponseException
@ExceptionHandler(AIServiceResponseException.class)
    public ResponseEntity<AIServiceErrorResponse> handlerAIServiceResponseException(AIServiceResponseException ex,HttpServletRequest request){

         log.error("AI service failed", ex);

        AIServiceErrorResponse aiErrorResponse= AIServiceErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("AI_RESPONSE_ERROR")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(aiErrorResponse);
    }

    // JWTAuthenticationException
    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handlerJwtAuthenticationException(JwtAuthenticationException ex,HttpServletRequest request){

        log.error("JWT AUTHENTICATION FAILED ", ex);

        ErrorResponse errorResponse= ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("JWT_AUTHENTICATION_FAILED")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }


}
