package com.bnbnac.moviestream.controller;

import com.bnbnac.moviestream.SessionAttribute;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final String password = System.getenv("MOVIESTREAM");

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/")
    public String postPassword(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        if (this.password.equals(request.getParameter("password"))) {
            String userIp = request.getRemoteAddr();
            String sessionID = session.getId();

            session.setAttribute(SessionAttribute.USER_IP.getValue(), userIp);

            Cookie userIpCookie = new Cookie(SessionAttribute.USER_IP.getValue(), userIp);
            Cookie sessionIDCookie = new Cookie(SessionAttribute.SESSION_ID.getValue(), sessionID);
            response.addCookie(userIpCookie);
            response.addCookie(sessionIDCookie);

            return "redirect:/movies";
        }
        return "home";
    }
}
