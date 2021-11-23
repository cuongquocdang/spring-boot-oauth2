package com.example.auth.exception;

import com.example.auth.dto.response.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    protected ResponseEntity<RestErrorResponse> handleAppException(AppException exception, WebRequest request) {
        log.error("App Exception occurs with message {} and status code {}.",
                exception.getMessage(), exception.getStatusCode());
        return RestErrorResponse.builder()
                .status(exception.getStatusCode())
                .message(exception.getMessage())
                .path(getPath(request))
                .entity();
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<RestErrorResponse> handleAllExceptions(Exception exception, WebRequest request) {
        log.error("Global Exception occurs with message {}.", exception.getMessage());
        return handleEveryException(exception, request);
    }

    protected ResponseEntity<RestErrorResponse> handleEveryException(Exception ex, WebRequest request) {
        return RestErrorResponse.builder()
                .status(INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .path(getPath(request))
                .entity();
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).substring(4);
    }
}
