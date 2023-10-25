package com.bnbnac.moviestream.service;

import com.bnbnac.moviestream.constant.EnvironmentVariable;
import com.bnbnac.moviestream.constant.FileExtension;
import com.bnbnac.moviestream.exception.LoadMovieException;
import com.bnbnac.moviestream.exception.LoadVariableException;
import com.bnbnac.moviestream.model.Movie;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.bnbnac.moviestream.utils.Utility.isNullOrBlank;

@Service
public class MovieService {
    private final List<Movie> movies;
    private final RestTemplate restTemplate;
    private final String storage;
    private final String movieServer;

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.storage = System.getenv(EnvironmentVariable.MOVIE_STORAGE);
        this.movieServer = System.getenv(EnvironmentVariable.MOVIE_SERVER);
        if (isNullOrBlank(storage)) {
            throw new LoadVariableException("movie storage path not found. check $MOVIE_STORAGE");
        }
        if (isNullOrBlank(movieServer)) {
            throw new LoadVariableException("movie server url not found. check $MOVIE_SERVER");
        }
        movies = loadMovies();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    private List<Movie> loadMovies() {
        List<Movie> movies = collectMovies(Paths.get(storage));

        if (movies.isEmpty()) {
            throw new LoadMovieException("no movies found in the storage");
        }

        movies.sort(Comparator.comparing(Movie::getMovieName));
        return movies;
    }

    private List<Movie> collectMovies(Path storagePath) {
        List<Movie> movies = new ArrayList<>();
        int movieId = 0;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(storagePath)) {
            for (Path path : stream) {
                if (!isHiddenFile(path)) {
                    movies.add(buildMovie(path, movieId++));
                }
            }
        } catch (IOException e) {
            throw new LoadMovieException("collectMovies: check the exact movie storage path");
        }
        return movies;
    }

    private boolean isHiddenFile(Path path) {
        return path.getFileName().toString().startsWith(".");
    }

    private Movie buildMovie(Path path, int movieId) {
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setMovieServer(movieServer);
        movie.setMovieName(path.toString().split("/")[5]);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path file : stream) {
                setMovieByFile(movie, file);
            }
        } catch (IOException e) {
            throw new LoadMovieException("buildMovie: check the exact movie storage path");
        }

        return movie;
    }

    private void setMovieByFile(Movie movie, Path file) {
        if (!isHiddenFile(file)) {
            String source = file.toString();
            String extension = getExtension(source);
            findWhichExtension(movie, source, extension);
        }
    }

    private void findWhichExtension(Movie movie, String source, String extension) {
        if (FileExtension.videoExtensions.contains(extension)) {
            movie.setVideoPath(convertRelativeURL(source));
            return;
        }
        if (FileExtension.subtitleExtensions.contains(extension)) {
            movie.setSubtitlePath(convertRelativeURL(source));
            return;
        }
        if (FileExtension.thumbnailExtensions.contains(extension)) {
            movie.setThumbnailPath(convertRelativeURL(source));
        }
    }

    private String convertRelativeURL(String source) {
        if (source.startsWith(storage)) {
            return source.split(storage)[1];
        }
        return "";
    }

    private String getExtension(String source) {
        return source.split("\\.")[source.split("\\.").length - 1];
    }

    public Movie findById(int id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return new Movie();
    }
}
