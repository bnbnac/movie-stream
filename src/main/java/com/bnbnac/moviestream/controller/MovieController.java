package com.bnbnac.moviestream.controller;

import com.bnbnac.moviestream.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.Path;
import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public String movies(Model model) {
        List<Path> files = movieService.getMovies();
        model.addAttribute("files", files);

        return "movies";
    }
}
