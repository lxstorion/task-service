package com.tensei.tasks.exception.handler;

import com.tensei.tasks.domain.dto.ErrorResponse;
import com.tensei.tasks.exception.FailedAuthenticationException;
import com.tensei.tasks.exception.ResourceNotFoundException;
import com.tensei.tasks.exception.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse(ex, request, status));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        int status = HttpStatus.UNAUTHORIZED.value();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createErrorResponse(ex, request, status));
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException ex, WebRequest request) {
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(ex, request, status));
    }

    @ExceptionHandler(FailedAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleFailedAuthenticationException(FailedAuthenticationException ex, WebRequest request) {
        int status = HttpStatus.UNAUTHORIZED.value();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createErrorResponse(ex, request, status));
    }

    private ErrorResponse createErrorResponse(RuntimeException ex, WebRequest request, int status) {
        return new ErrorResponse(
                status,
                ex.getMessage(),
                request.getDescription(false).replace("uri=", ""),
                LocalDateTime.now()
        );
    }
}
