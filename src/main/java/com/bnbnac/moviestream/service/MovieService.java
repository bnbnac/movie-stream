package com.bnbnac.moviestream.service;

import com.bnbnac.moviestream.constant.EnvironmentVariable;
import com.bnbnac.moviestream.exception.LoadMovieException;
import com.bnbnac.moviestream.exception.LoadVariableException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.bnbnac.moviestream.utils.Utility.isNullOrBlank;

@Service
public class MovieService {
    private final List<Path> movies;

    public MovieService() {
        String storage = System.getenv(EnvironmentVariable.MOVIE_STORAGE);
        if (isNullOrBlank(storage)) {
            throw new LoadVariableException("movie storage path not found. $MOVIE_STORAGE : ",
                    EnvironmentVariable.MOVIE_STORAGE);
        }
        movies = load(storage);
    }

    private List<Path> load(String storage) {
        List<Path> files = collectFiles(Paths.get(storage));

        if (files.isEmpty()) {
            throw new LoadMovieException("no movies found in the storage");
        }

        Collections.sort(files);
        return files;
    }

    private List<Path> collectFiles(Path storagePath) {
        List<Path> files = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(storagePath)) {
            for (Path file : stream) {
                neglectHiddenFile(file, files);
            }
        } catch (IOException e) {
            throw new LoadMovieException("check the exact movie storage path");
        }
        return files;
    }

    private void neglectHiddenFile(Path file, List<Path> files) {
        if (!file.getFileName().toString().startsWith(".")) {
            files.add(file);
        }
    }

    public List<Path> getMovies() {
        return movies;
    }
}
