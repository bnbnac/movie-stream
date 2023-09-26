package com.bnbnac.moviestream.exception;

public class LoadVariableException extends RuntimeException implements MessageHiddenException {
    public LoadVariableException(String message) {
        super(message);
    }
}
