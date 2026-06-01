package com.substring.helpdesk.exception.custom;


public class ResourceNotFoundException extends RuntimeException{
    ResourceNotFoundException(String message){
        super(message);
    }
}
