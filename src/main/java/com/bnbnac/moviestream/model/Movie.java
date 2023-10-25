package com.bnbnac.moviestream.model;

public class Movie {
    private int id;
    private String movieName;
    private String videoPath;
    private String subtitlePath;
    private String thumbnailPath;
    private String movieServer;

    public Movie() {
    }

    public Movie(int id, String movieName, String videoPath, String subtitlePath, String thumbnailPath, String movieServer) {
        this.id = id;
        this.movieName = movieName;
        this.videoPath = videoPath;
        this.subtitlePath = subtitlePath;
        this.thumbnailPath = thumbnailPath;
        this.movieServer = movieServer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setSubtitlePath(String subtitlePath) {
        this.subtitlePath = subtitlePath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public void setMovieServer(String movieServer) {
        this.movieServer = movieServer;
    }

    public int getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public String getSubtitlePath() {
        return subtitlePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public String getMovieServer() {
        return movieServer;
    }
}
