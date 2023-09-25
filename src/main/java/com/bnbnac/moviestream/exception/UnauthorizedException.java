package com.bnbnac.moviestream.exception;

public class UnauthorizedException extends RuntimeException {
    private static final String MESSAGE = "login please";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}
