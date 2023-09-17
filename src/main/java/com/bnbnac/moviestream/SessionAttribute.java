package com.bnbnac.moviestream;

public enum SessionAttribute {
    LOGGED_IN("loggedIn"),
    ;

    private final String value;

    SessionAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
