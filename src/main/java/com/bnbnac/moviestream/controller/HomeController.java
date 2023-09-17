package com.bnbnac.moviestream.controller;

import com.bnbnac.moviestream.SessionAttribute;
import com.bnbnac.moviestream.service.PasswordService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private final PasswordService passwordService;

    public HomeController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/")
    public String postPassword(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        if (passwordService.match(request.getParameter("password"))) {
            session.setAttribute(SessionAttribute.USER_IP.getValue(), request.getRemoteAddr());
            response.addCookie(new Cookie(SessionAttribute.USER_IP.getValue(), request.getRemoteAddr()));
            response.addCookie(new Cookie(SessionAttribute.SESSION_ID.getValue(), session.getId()));
            return "redirect:/movies";
        }
        return "home";
    }
}
