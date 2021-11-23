package com.example.auth.dto.request;

import com.example.auth.dto.SocialProvider;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class RegisterRequest {

    private Long userID;

    private String providerUserId;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;

    private SocialProvider socialProvider;

    @Size(min = 6)
    private String password;

    @NotEmpty
    private String matchingPassword;

}
