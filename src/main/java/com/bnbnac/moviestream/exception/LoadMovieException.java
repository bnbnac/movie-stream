package com.bnbnac.moviestream.exception;

public class LoadMovieException extends RuntimeException implements MessageHiddenException {
    public LoadMovieException(String message) {
        super(message);
    }
}
