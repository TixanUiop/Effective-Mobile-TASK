package com.togithub.effectivemobilejavatask.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super("User already exists");
    }
}
