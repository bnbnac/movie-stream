package com.bnbnac.moviestream.model;

public class Movie {
    private int id;
    private String movieName;
    private String videoPath;
    private String subtitlePath;
    private String thumbnailPath;

    public Movie(int id, String movieName, String videoPath, String subtitlePath, String thumbnailPath) {
        this.id = id;
        this.movieName = movieName;
        this.videoPath = videoPath;
        this.subtitlePath = subtitlePath;
        this.thumbnailPath = thumbnailPath;
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
}
