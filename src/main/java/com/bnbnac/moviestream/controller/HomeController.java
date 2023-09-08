package com.bnbnac.moviestream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final String password = System.getenv("MOVIESTREAM");

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/")
    public String postPassword(@RequestParam("password") String password) {
        if (this.password.equals(password)) {
            return "movies";
        }
        return "home";
    }
}
