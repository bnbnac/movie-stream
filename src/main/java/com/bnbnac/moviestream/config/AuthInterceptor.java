package com.bnbnac.moviestream.config;

import com.bnbnac.moviestream.SessionAttribute;
import com.bnbnac.moviestream.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute(SessionAttribute.LOGGED_IN.getValue()) == null) {
            throw new UnauthorizedException();
        }
        return true;
    }
}
