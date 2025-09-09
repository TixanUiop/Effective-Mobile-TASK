package com.togithub.effectivemobilejavatask.exception;

public class UnauthorizedCardAccessException extends RuntimeException {
    public UnauthorizedCardAccessException(String message) {
        super("Unauthorized: card does not belong to current user");
    }
}
