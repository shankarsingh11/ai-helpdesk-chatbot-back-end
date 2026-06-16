package com.substring.helpdesk.security.oauth;

import com.substring.helpdesk.entity.User;
import com.substring.helpdesk.entity.enm.AuthProvider;
import com.substring.helpdesk.repository.UserRepo;
import com.substring.helpdesk.security.oauth.userinfo.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends
DefaultOAuth2UserService{

    private final UserRepo userRepo;
    private final OAuth2UserInfoFactory auth2UserInfoFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest){

     OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

     String  registrationId = oAuth2UserRequest
             .getClientRegistration().getRegistrationId();

        log.info("OAuth Login Provider: {}", registrationId);

        OAuth2UserInfo userInfo= auth2UserInfoFactory.getOAuth2UserInfo(
                registrationId,
                oAuth2User.getAttributes()
        );

        User user = saveOrUpdateUser(
                userInfo,
                registrationId
        );

        return new CustomOAuth2User(user,oAuth2User.getAttributes());

    }


    private User saveOrUpdateUser(
            OAuth2UserInfo userInfo,
            String registrationId) {

        return userRepo.findByEmail(userInfo.getEmail())
                .map(existingUser -> {

                    existingUser.setName(
                            userInfo.getName()
                    );

                    existingUser.setProfilePicture(
                            userInfo.getImageUrl()
                    );

                    return userRepo.save(existingUser);
                })
                .orElseGet(() -> {

                    User user = User.builder()
                            .name(userInfo.getName())
                            .email(userInfo.getEmail())
                            .profilePicture(
                                    userInfo.getImageUrl()
                            )
                            .providerId(
                                    userInfo.getId()
                            )
                            .provider(
                                    AuthProvider.valueOf(
                                            registrationId.toUpperCase()
                                    )
                            )
                            .enabled(true)
                            .build();

                    return userRepo.save(user);
                });
    }

}
