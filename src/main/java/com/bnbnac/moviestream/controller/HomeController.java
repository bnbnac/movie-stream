package com.bnbnac.moviestream.controller;

import com.bnbnac.moviestream.service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String postPassword(HttpServletRequest request, Model model) {
        if (passwordService.match(request.getParameter("password"))) {
            request.getSession();
            return "redirect:/movies";
        }
        model.addAttribute("error", true);
        return "home";
    }
}
