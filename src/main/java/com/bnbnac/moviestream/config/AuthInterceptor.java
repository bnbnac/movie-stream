package com.bnbnac.moviestream.config;

import com.bnbnac.moviestream.SessionAttribute;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] requestCookies = request.getCookies();
        String[] cookies = new String[2];

        // exception 처리하면 더 예뻐지고 array도 안 쓸 수 있을 것 같지만 귀찮음
        if (requestCookies == null || !loadCookies(requestCookies, cookies)) {
            response.sendRedirect("/");
            return false;
        }

        String userIpCookie = cookies[0];
        String sessionIDCookie = cookies[1];
        String userIp = request.getRemoteAddr();
        String sessionID = request.getSession().getId();

        if (!validateSession(userIpCookie, sessionIDCookie, userIp, sessionID)) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }

    private boolean validateSession(String userIpCookie, String sessionIDCookie, String userIp, String sessionID) {
        if (userIpCookie == null || sessionIDCookie == null) {
            return false;
        }
        return userIpCookie.equals(userIp) && sessionIDCookie.equals(sessionID);
    }

    private boolean loadCookies(Cookie[] requestCookies, String[] cookies) {
        for (Cookie cookie : requestCookies) {
            if (SessionAttribute.USER_IP.getValue().equals(cookie.getName())) {
                cookies[0] = cookie.getValue();
            }
            if (SessionAttribute.SESSION_ID.getValue().equals(cookie.getName())) {
                cookies[1] = cookie.getValue();
            }
            if (cookies[0] != null && cookies[1] != null) {
                return true;
            }
        }
        return false;
    }
}
