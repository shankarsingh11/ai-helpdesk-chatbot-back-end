package com.substring.helpdesk.security.oauth.verifier;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.substring.helpdesk.exception.custom.OAuthAuthenticationException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@Component
public class GoogleTokenVerifierImpl implements GoogleTokenVerifier {

    private static final NetHttpTransport TRANSPORT = new NetHttpTransport();
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    private GoogleIdTokenVerifier verifier;

    @PostConstruct
    public void init() {

        verifier = new GoogleIdTokenVerifier.Builder(
                TRANSPORT,
                JSON_FACTORY
        )
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        log.info("GoogleTokenVerifier initialized successfully.");
    }

    @Override
    public GoogleIdToken.Payload verify(String idToken) {

        if (idToken == null || idToken.isBlank()) {
            throw new OAuthAuthenticationException(
                    "Google ID Token is required."
            );
        }

        try {

            log.debug("Verifying Google ID Token...");

            GoogleIdToken googleIdToken = verifier.verify(idToken);

            if (googleIdToken == null) {
                throw new OAuthAuthenticationException(
                        "Invalid Google ID Token."
                );
            }

            GoogleIdToken.Payload payload = googleIdToken.getPayload();

            if (payload == null) {
                throw new OAuthAuthenticationException(
                        "Unable to read Google token payload."
                );
            }

            if (payload.getEmail() == null || payload.getEmail().isBlank()) {
                throw new OAuthAuthenticationException(
                        "Google account does not contain an email address."
                );
            }

            if (!Boolean.TRUE.equals(payload.getEmailVerified())) {
                throw new OAuthAuthenticationException(
                        "Google email is not verified."
                );
            }

            log.debug(
                    "Google user authenticated successfully. Email: {}, Subject: {}",
                    payload.getEmail(),
                    payload.getSubject()
            );

            return payload;

        } catch (GeneralSecurityException | IOException ex) {

            log.error("Failed to verify Google ID Token.", ex);

            throw new OAuthAuthenticationException(
                    "Unable to verify Google ID Token.",
                    ex
            );
        }
    }
}