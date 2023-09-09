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
            session.setAttribute("ip", request.getRemoteAddr());
            String sessionID = session.getId();
            Cookie sessionCookie = new Cookie(SessionAttribute.SESSION_ID.getValue(), sessionID);
            response.addCookie(sessionCookie);

            return "redirect:/movies";
        }
        return "home";
    }
}
