package com.bnbnac.moviestream.exception;

public class LoadPasswordException extends RuntimeException {
    private static final String MESSAGE = "server cannot load the password";

    public LoadPasswordException() {
        super(MESSAGE);
    }
}
