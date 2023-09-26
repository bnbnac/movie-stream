package com.bnbnac.moviestream.service;

import com.bnbnac.moviestream.constant.EnvironmentVariable;
import com.bnbnac.moviestream.exception.LoadVariableException;
import org.springframework.stereotype.Service;

import static com.bnbnac.moviestream.utils.Utility.isNullOrBlank;

@Service
public class PasswordService {
    private final String password;

    public PasswordService() {
        password = System.getenv(EnvironmentVariable.MOVIE_STREAM);
        if (isNullOrBlank(password)) {
            throw new LoadVariableException("password not found. check $MOVIE_STREAM");
        }
    }

    public boolean match(String password) {
        return this.password.equals(password);
    }
}
