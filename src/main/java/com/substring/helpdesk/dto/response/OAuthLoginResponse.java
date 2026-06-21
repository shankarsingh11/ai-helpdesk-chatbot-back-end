package com.substring.helpdesk.dto.response;

import com.substring.helpdesk.entity.enm.AuthProvider;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthLoginResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Integer userId;

    private String name;

    private String email;

    private String profilePicture;

    private AuthProvider provider;

    private String message;
}