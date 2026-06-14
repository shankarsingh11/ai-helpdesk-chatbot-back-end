package com.substring.helpdesk.service.common;

import com.substring.helpdesk.service.auth.GithubOAuth2UserInfo;
import com.substring.helpdesk.service.auth.GoogleOAuth2UserInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OAuth2UserInfoFactory {

    public OAuth2UserInfo getOAuth2UserInfo(
            String registrationid,
            Map<String, Object> attributes
    ){
        return switch (registrationid){
            case "google" -> new GoogleOAuth2UserInfo(attributes);
            case "github" -> new GithubOAuth2UserInfo(attributes);
            default -> throw new RuntimeException("Unsupported Provider");
        };
    }

}
