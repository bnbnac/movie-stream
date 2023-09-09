package com.bnbnac.moviestream.config;

import com.bnbnac.moviestream.SessionAttribute;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String userIpCookie = null;
        String sessionIDCookie = null;

        if (cookies == null) {
            return false;
        }
        for (Cookie cookie : cookies) {
            if (SessionAttribute.USER_IP.getValue().equals(cookie.getName())) {
                userIpCookie = cookie.getValue();
            }
            if (SessionAttribute.SESSION_ID.getValue().equals(cookie.getName())) {
                sessionIDCookie = cookie.getValue();
            }
            if (userIpCookie != null && sessionIDCookie != null) {
                break;
            }
        }

        String userIP = request.getRemoteAddr();
        HttpSession session = request.getSession();

        if (userIpCookie == null || sessionIDCookie == null) {
            return false;
        }
        return userIP.equals(session.getAttribute(SessionAttribute.USER_IP.getValue())) &&
                sessionIDCookie.equals(session.getId());
    }
}
