package com.example.auth.dto.response;

import com.example.auth.dto.UserInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtResponse {
    @NonNull
    @JsonProperty("jwt_token")
    private String jwtToken;

    @NonNull
    @JsonProperty("user_info")
    private UserInfo userInfo;
}
