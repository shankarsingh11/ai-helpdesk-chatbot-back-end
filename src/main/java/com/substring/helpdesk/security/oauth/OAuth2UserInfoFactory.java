package com.substring.helpdesk.security.oauth;

import com.substring.helpdesk.security.oauth.userinfo.GitHubOAuth2UserInfo;
import com.substring.helpdesk.security.oauth.userinfo.OAuth2UserInfo;

import com.substring.helpdesk.security.oauth.userinfo.GoogleOAuth2UserInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OAuth2UserInfoFactory {

    public OAuth2UserInfo getOAuth2UserInfo(
            String registrationId,
            Map<String, Object> attributes
    )
    {
        if ("google".equalsIgnoreCase(registrationId)) {
            return new GoogleOAuth2UserInfo(attributes);
        }

        if ("github".equalsIgnoreCase(registrationId)) {
            return new GitHubOAuth2UserInfo(attributes);
        }

        throw new IllegalArgumentException(
                "Unsupported OAuth Provider: "
                        + registrationId
        );

    }

}
