package com.bnbnac.moviestream.exception;

public class LoadPassword extends RuntimeException {
    private static final String MESSAGE = "server cannot load password";

    public LoadPassword() {
        super(MESSAGE);
    }
}
