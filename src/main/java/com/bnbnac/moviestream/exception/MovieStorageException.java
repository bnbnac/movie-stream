package com.bnbnac.moviestream.exception;

public class MovieStorageException extends RuntimeException {
    private static final String MESSAGE = "check movie storage path";

    public MovieStorageException() {
        super(MESSAGE);
    }
}
