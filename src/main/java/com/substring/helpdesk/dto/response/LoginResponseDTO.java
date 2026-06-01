package com.substring.helpdesk.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
public class LoginResponseDTO {
    private String message;
    private String username;
    private String email;
}
