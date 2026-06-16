package com.substring.helpdesk.security.oauth.userinfo;

public interface OAuth2UserInfo {

    String getId();
    String getName();
    String getEmail();
    String getImageUrl();
}