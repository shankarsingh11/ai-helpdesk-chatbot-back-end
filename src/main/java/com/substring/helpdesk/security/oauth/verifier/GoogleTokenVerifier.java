package com.substring.helpdesk.security.oauth.verifier;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public interface GoogleTokenVerifier {
    GoogleIdToken.Payload verify(String idToken);
}
