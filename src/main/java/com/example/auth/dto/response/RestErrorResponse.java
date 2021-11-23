package com.example.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);
    private int status;
    private String error;
    private String message;
    private String path;

    public static RestErrorResponseBuilder builder() {
        return new RestErrorResponseBuilder();
    }

}
