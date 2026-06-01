package com.substring.helpdesk.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class RegisterRequestDTO {

    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "username is required")
    private String username;

    @Size(min = 6, message = "password must be at least 6 characters")
    @NotNull
    private String password;

    @NotNull(message = "email is required")
    @Email(message = "please provide a valid email format")
    private String email;


}
