package com.substring.helpdesk.service.oauth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.substring.helpdesk.dto.request.GithubLoginRequest;
import com.substring.helpdesk.dto.request.GoogleLoginRequest;
import com.substring.helpdesk.dto.response.OAuthLoginResponse;
import com.substring.helpdesk.entity.User;
import com.substring.helpdesk.entity.enm.AuthProvider;
import com.substring.helpdesk.repository.UserRepo;
import com.substring.helpdesk.security.jwt.JwtService;
import com.substring.helpdesk.security.oauth.OAuth2UserInfoFactory;
import com.substring.helpdesk.security.oauth.client.GitHubOAuthClient;
import com.substring.helpdesk.security.oauth.userinfo.OAuth2UserInfo;
import com.substring.helpdesk.security.oauth.verifier.GoogleTokenVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthAuthenticationServiceImpl implements OAuthAuthenticationService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2UserInfoFactory userInfoFactory;
    private final GoogleTokenVerifier googleTokenVerifier;
    private final GitHubOAuthClient gitHubOAuthClient;

    @Override
    public OAuthLoginResponse googleLogin(GoogleLoginRequest request)
            throws GeneralSecurityException, IOException {

        log.info("Authenticating Google user...");

        GoogleIdToken.Payload payload =
                googleTokenVerifier.verify(request.getIdToken());

        OAuth2UserInfo userInfo =
                userInfoFactory.getOAuth2UserInfo("google", payload);

        User user = findOrCreateUser(userInfo, AuthProvider.GOOGLE);

        return buildResponse(user);
    }

    @Override
    public OAuthLoginResponse githubLogin(GithubLoginRequest request) {

        log.info("Authenticating GitHub user...");

        Map<String, Object> attributes =
                gitHubOAuthClient.getUserInfo(request.getAccessToken());

        OAuth2UserInfo userInfo =
                userInfoFactory.getOAuth2UserInfo("github", attributes);

        if (userInfo.getEmail() == null || userInfo.getEmail().isBlank()) {
            throw new RuntimeException(
                    "GitHub account does not expose an email address."
            );
        }

        User user = findOrCreateUser(userInfo, AuthProvider.GITHUB);

        return buildResponse(user);
    }

    /**
     * Find existing user or create a new one.
     */
    private User findOrCreateUser(OAuth2UserInfo userInfo,
                                  AuthProvider provider) {

        Optional<User> optionalUser =
                userRepo.findByEmail(userInfo.getEmail());

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        return createUser(userInfo, provider);
    }

    /**
     * Create OAuth user.
     */
    private User createUser(OAuth2UserInfo userInfo,
                            AuthProvider provider) {

        User user = new User();

        user.setName(userInfo.getName());
        user.setEmail(userInfo.getEmail());
        user.setProfilePicture(userInfo.getImageUrl());

        user.setProvider(provider);
        user.setProviderId(userInfo.getId());

        user.setEnabled(true);

        user.setPassword(
                passwordEncoder.encode(UUID.randomUUID().toString())
        );

        return userRepo.save(user);
    }

    /**
     * Generate JWT response.
     */
    private OAuthLoginResponse buildResponse(User user) {

        String token = jwtService.generateToken(user.getEmail());

        return OAuthLoginResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .provider(user.getProvider().name())
                .build();
    }
}