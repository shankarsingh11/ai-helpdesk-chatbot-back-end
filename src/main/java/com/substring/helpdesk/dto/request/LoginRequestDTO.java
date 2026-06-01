package com.substring.helpdesk.dto.request;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginRequestDTO {

    @NotNull(message = "enter username or email")
    private String usernameOrEmail;

    @Size(min = 6, message = "enter your correct password")
    @NotNull(message = "Password is required")
    private String password;


}
