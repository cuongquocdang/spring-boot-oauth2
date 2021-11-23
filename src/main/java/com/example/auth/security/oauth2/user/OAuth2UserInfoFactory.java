package com.example.auth.security.oauth2.user;

import com.example.auth.dto.SocialProvider;
import com.example.auth.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (SocialProvider.GOOGLE.getProviderType().equalsIgnoreCase(registrationId)) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (SocialProvider.FACEBOOK.getProviderType().equalsIgnoreCase(registrationId)) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (SocialProvider.GITHUB.getProviderType().equalsIgnoreCase(registrationId)) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }

}
