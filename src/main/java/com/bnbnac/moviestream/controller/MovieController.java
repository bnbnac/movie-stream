package com.bnbnac.moviestream.controller;

import com.bnbnac.moviestream.SessionAttribute;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {
    @GetMapping("/movies")
    public String movies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String sessionCookieValue = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SessionAttribute.SESSION_ID.getValue().equals(cookie.getName())) {
                    sessionCookieValue = cookie.getValue();
                    break;
                }
            }
        }

        System.out.println(sessionCookieValue);
        return "movies";
    }
}
