package com.substring.helpdesk.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
public class RegisterResponseDTO {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String message;

}
