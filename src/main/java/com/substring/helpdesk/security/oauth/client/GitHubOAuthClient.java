package com.substring.helpdesk.security.oauth.client;

import java.util.Map;

public interface GitHubOAuthClient {
    Map<String, Object> getUserInfo(String accessToken);
}
