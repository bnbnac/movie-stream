package com.bnbnac.moviestream;

public enum SessionAttribute {
    SESSION_ID("sessionID");

    private final String value;

    SessionAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
