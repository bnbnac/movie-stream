package com.bnbnac.moviestream.exception;

public class LoadVariableException extends RuntimeException implements MessageHiddenException {
    public LoadVariableException(String message, String variableName) {
        super(message + System.getenv(variableName));
    }
}
