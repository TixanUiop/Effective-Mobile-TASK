package com.togithub.effectivemobilejavatask.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super("Insufficient funds");
    }
}
