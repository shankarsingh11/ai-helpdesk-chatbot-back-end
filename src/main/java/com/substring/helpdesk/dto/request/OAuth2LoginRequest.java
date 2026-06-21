package com.substring.helpdesk.dto.request;

import lombok.Data;

@Data
public class OAuth2LoginRequest {

    private String provider;// google, github,linkedin

    private String code; // github, linkedin

    private String idToken; // google
}