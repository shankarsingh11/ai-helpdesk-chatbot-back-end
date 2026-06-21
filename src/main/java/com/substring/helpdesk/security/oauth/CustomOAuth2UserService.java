package com.substring.helpdesk.security.oauth;

import com.substring.helpdesk.entity.User;
import com.substring.helpdesk.entity.enm.AuthProvider;
import com.substring.helpdesk.repository.UserRepo;
import com.substring.helpdesk.security.oauth.userinfo.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService
        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepo userRepo;
    private final OAuth2UserInfoFactory userInfoFactory;

    @Override
    public OAuth2User loadUser(
            OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        DefaultOAuth2UserService delegate =
                new DefaultOAuth2UserService();

        OAuth2User oauth2User =
                delegate.loadUser(userRequest);

        String registrationId =
                userRequest
                        .getClientRegistration()
                        .getRegistrationId();

        OAuth2UserInfo userInfo =
                userInfoFactory.getOAuth2UserInfo(
                        registrationId,
                        oauth2User.getAttributes()
                );

        return processOAuthUser(
                registrationId,
                userInfo,
                oauth2User
        );
    }

    private OAuth2User processOAuthUser(
            String registrationId,
            OAuth2UserInfo userInfo,
            OAuth2User oauth2User) {

        if (userInfo.getEmail() == null) {
            throw new OAuth2AuthenticationException(
                    "Email not found from OAuth provider"
            );
        }

        User user = userRepo
                .findByEmail(userInfo.getEmail())
                .orElseGet(() ->
                        createNewUser(
                                registrationId,
                                userInfo
                        )
                );

        updateExistingUser(user, userInfo);

        userRepo.save(user);

        return new CustomOAuth2User(
                user,
                oauth2User.getAttributes()
        );
    }

    private User createNewUser(
            String registrationId,
            OAuth2UserInfo userInfo) {

        return User.builder()
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .profilePicture(userInfo.getImageUrl())
                .provider(getProvider(registrationId))
                .providerId(userInfo.getId())
                .enabled(true)
                .build();
    }

    private void updateExistingUser(
            User user,
            OAuth2UserInfo userInfo) {

        user.setName(userInfo.getName());
        user.setProfilePicture(
                userInfo.getImageUrl()
        );
    }

    private AuthProvider getProvider(
            String registrationId) {

        return switch (registrationId.toLowerCase()) {

            case "google" ->
                    AuthProvider.GOOGLE;

            case "github" ->
                    AuthProvider.GITHUB;

            case "linkedin" ->
                    AuthProvider.LINKEDIN;

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported Provider: "
                                    + registrationId
                    );
        };
    }
}