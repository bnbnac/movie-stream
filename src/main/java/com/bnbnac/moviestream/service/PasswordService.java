package com.bnbnac.moviestream.service;

import com.bnbnac.moviestream.exception.LoadPasswordException;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private final String password;

    public PasswordService() {
        this.password = System.getenv("MOVIESTREAM");
        if (password == null) {
            throw new LoadPasswordException();
        }
    }

    public boolean match(String password) {
        return this.password.equals(password);
    }
}
