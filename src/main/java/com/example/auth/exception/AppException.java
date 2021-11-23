package com.example.auth.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Slf4j
public class AppException extends RuntimeException {
    private int statusCode;

    public AppException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public AppException(HttpStatus httpStatus, String message) {
        super(message);
        this.statusCode = httpStatus.value();
    }

}
