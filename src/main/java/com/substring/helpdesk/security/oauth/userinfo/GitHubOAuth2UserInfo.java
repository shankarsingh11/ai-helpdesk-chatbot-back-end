package com.substring.helpdesk.security.oauth.userinfo;



import lombok.Getter;

import java.util.Map;

@Getter
public class GitHubOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public GitHubOAuth2UserInfo(
            Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return String.valueOf(
                attributes.get("id")
        );
    }

    @Override
    public String getName() {

        Object name =
                attributes.get("name");

        if (name == null) {
            return (String)
                    attributes.get("login");
        }

        return name.toString();
    }

    @Override
    public String getUsername() {
        return "";
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
        return (String)
                attributes.get("avatar_url");
    }
}
