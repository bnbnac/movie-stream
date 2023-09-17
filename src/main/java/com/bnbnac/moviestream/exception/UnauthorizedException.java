package com.bnbnac.moviestream.exception;

public class UnauthorizedException extends RuntimeException{
    private static final String MESSAGE = "unauthorized user";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}
