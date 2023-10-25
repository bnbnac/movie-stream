package com.bnbnac.moviestream.controller;

import com.bnbnac.moviestream.model.Movie;
import com.bnbnac.moviestream.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public String movies(Model model) {
        List<Movie> movies = movieService.getMovies();
        model.addAttribute("movies", movies);

        return "movies";
    }

    @GetMapping("/watch/{id}")
    public String watch(@PathVariable int id, Model model) {
        Movie movie = movieService.findById(id);
        model.addAttribute("movie", movie);

        return "watch";
    }
}
