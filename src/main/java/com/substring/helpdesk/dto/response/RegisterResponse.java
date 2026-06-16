package com.substring.helpdesk.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private Integer id;
    private String name;
    private String email;
    private String message;

}
