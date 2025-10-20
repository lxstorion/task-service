package com.tensei.tasks.exception;

public class UnknownPermissionException extends RuntimeException {
    public UnknownPermissionException(String message) {
        super(message);
    }
}
