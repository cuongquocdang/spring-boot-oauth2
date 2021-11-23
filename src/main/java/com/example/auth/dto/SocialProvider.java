package com.example.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SocialProvider {
    FACEBOOK("facebook"),
    GOOGLE("google"),
    GITHUB("github"),
    LOCAL("local");

    private final String providerType;
}
