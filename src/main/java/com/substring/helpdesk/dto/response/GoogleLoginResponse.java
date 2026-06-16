package com.substring.helpdesk.dto.response;

import lombok.Data;

@Data
public class GoogleLoginResponse {
    private String accessToken;
    private String refreshToken;
}
