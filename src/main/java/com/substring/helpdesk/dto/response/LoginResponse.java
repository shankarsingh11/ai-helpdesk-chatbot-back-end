package com.substring.helpdesk.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String status;
    private String message;
    private String token;
}
