package com.example.auth.dto.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

public class RestErrorResponseBuilder {
    private int status;
    private String error;
    private String message;
    private String path;

    public RestErrorResponseBuilder status(int status) {
        this.status = status;
        return this;
    }

    public RestErrorResponseBuilder status(HttpStatus status) {
        this.status = status.value();
        if (status.isError()) {
            this.error = status.getReasonPhrase();
        }
        return this;
    }

    public RestErrorResponseBuilder error(String error) {
        this.error = error;
        return this;
    }

    public RestErrorResponseBuilder exception(ResponseStatusException exception) {
        HttpStatus status = exception.getStatus();
        this.status = status.value();
        if (!Objects.requireNonNull(exception.getReason()).isBlank()) {
            this.message = exception.getReason();
        }
        if (status.isError()) {
            this.error = status.getReasonPhrase();
        }
        return this;
    }

    public RestErrorResponseBuilder message(String message) {
        this.message = message;
        return this;
    }

    public RestErrorResponseBuilder path(String path) {
        this.path = path;
        return this;
    }

    public RestErrorResponse build() {
        RestErrorResponse response = new RestErrorResponse();
        response.setStatus(status);
        response.setError(error);
        response.setMessage(message);
        response.setPath(path);
        return response;
    }

    public ResponseEntity<RestErrorResponse> entity() {
        return ResponseEntity
                .status(status)
                .headers(HttpHeaders.EMPTY)
                .body(build());
    }

}
