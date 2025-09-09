package com.togithub.effectivemobilejavatask.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super("Invalid credentials");
    }
}
