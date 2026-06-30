package com.substring.helpdesk.security.oauth.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class GitHubOAuthClientImpl implements GitHubOAuthClient{

    @Override
    public Map<String, Object> getUserInfo(String accessToken) {
        return Map.of();
    }
}
