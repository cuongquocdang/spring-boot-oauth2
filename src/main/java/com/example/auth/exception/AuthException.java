package com.example.auth.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
@Setter
@Slf4j
public class AuthException extends AuthenticationException {
    private int statusCode;

    public AuthException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public AuthException(HttpStatus httpStatus, String message) {
        super(message);
        this.statusCode = httpStatus.value();
    }

}
