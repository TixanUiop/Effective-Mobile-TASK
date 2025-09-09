package com.togithub.effectivemobilejavatask.exception;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super("Amount must be positive");
    }
}
