package com.bnbnac.moviestream.service;

import com.bnbnac.moviestream.constant.EnvironmentVariable;
import com.bnbnac.moviestream.constant.FileExtension;
import com.bnbnac.moviestream.exception.LoadMovieException;
import com.bnbnac.moviestream.exception.LoadVariableException;
import com.bnbnac.moviestream.model.Movie;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.bnbnac.moviestream.utils.Utility.isNullOrBlank;

@Service
public class MovieService {
    private final List<Movie> movies;

    public MovieService() {
        String storage = System.getenv(EnvironmentVariable.MOVIE_STORAGE);
        if (isNullOrBlank(storage)) {
            throw new LoadVariableException("movie storage path not found. $MOVIE_STORAGE : ",
                    EnvironmentVariable.MOVIE_STORAGE);
        }
        movies = load(storage);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    private List<Movie> load(String storage) {
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
            throw new LoadMovieException("check the exact movie storage path");
        }
        return movies;
    }

    private boolean isHiddenFile(Path path) {
        return path.getFileName().toString().startsWith(".");
    }

    private Movie buildMovie(Path path, int movieId) {
        String movieName = getMovieName(path);
        String videoPath = "";
        String subtitlePath = "";
        String thumbnailPath = "";

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path file : stream) {
                //enum?
                if (!isHiddenFile(file)) {
                    String source = file.toString();
                    String extension = getExtension(source);
                    if (FileExtension.videoExtensions.contains(extension)) {
                        videoPath = source;
                        continue;
                    }
                    if (FileExtension.subtitleExtensions.contains(extension)) {
                        subtitlePath = source;
                        continue;
                    }
                    if (FileExtension.thumbnailExtensions.contains(extension)) {
                        thumbnailPath = source;
                    }
                }
            }
        } catch (IOException e) {
            throw new LoadMovieException("check the exact movie storage path");
        }

        return new Movie(movieId, movieName, videoPath, subtitlePath, thumbnailPath);
    }

    private String getMovieName(Path path) {
        return path.toString().split("/")[5];
    }

    private String getExtension(String source) {
        return source.split("\\.")[source.split("\\.").length - 1];
    }
}
