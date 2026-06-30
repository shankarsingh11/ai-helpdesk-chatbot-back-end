package com.substring.helpdesk.dto.response;

import com.substring.helpdesk.entity.enm.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthLoginResponse {

    private String token;

    private Integer userId;

    private String name;

    private String email;

    private String profilePicture;

    private String provider;

    private String message;
}