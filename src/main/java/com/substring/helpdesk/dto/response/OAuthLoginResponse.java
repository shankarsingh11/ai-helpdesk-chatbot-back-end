package com.substring.helpdesk.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthLoginResponse {
        private String message;
        private String token;
        private String status;

}
