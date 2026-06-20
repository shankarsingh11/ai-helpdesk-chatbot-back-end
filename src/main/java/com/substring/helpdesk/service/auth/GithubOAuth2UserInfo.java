package com.substring.helpdesk.service.user;

import com.substring.helpdesk.security.oauth.userinfo.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GithubOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getId() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public String getEmail() {
        return "";
    }

    @Override
    public String getImageUrl() {
        return "";
    }
}
