package com.bnbnac.moviestream.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {
    @GetMapping("/movies")
    public String movies(HttpServletRequest request) {
        return "movies";
    }
}
