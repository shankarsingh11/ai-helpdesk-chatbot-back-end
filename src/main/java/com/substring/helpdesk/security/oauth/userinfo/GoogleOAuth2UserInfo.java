package com.substring.helpdesk.security.oauth.userinfo;

import lombok.Getter;

import java.util.Map;

@Getter
public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public GoogleOAuth2UserInfo(
            Map<String, Object> attributes) {

        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {

        if(attributes.get("email") != null){
            return attributes.get("email").toString();
        }
        return null;
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
