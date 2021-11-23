package com.example.auth.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginRequest {
    @Email
    private String email;

    @NotEmpty
    private String password;
}
