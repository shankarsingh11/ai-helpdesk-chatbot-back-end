package com.substring.helpdesk.service.oauth;

import com.substring.helpdesk.dto.request.GithubLoginRequest;
import com.substring.helpdesk.dto.request.GoogleLoginRequest;
import com.substring.helpdesk.dto.response.OAuthLoginResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface OAuthAuthenticationService {
    OAuthLoginResponse googleLogin(GoogleLoginRequest request) throws GeneralSecurityException, IOException;
    OAuthLoginResponse githubLogin(GithubLoginRequest request);

}
