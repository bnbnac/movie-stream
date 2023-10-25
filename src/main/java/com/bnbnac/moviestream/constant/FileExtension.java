package com.bnbnac.moviestream.constant;

import java.util.HashSet;
import java.util.Set;

public class FileExtension {
    public static final Set<String> videoExtensions = new HashSet<>();
    public static final Set<String> subtitleExtensions = new HashSet<>();
    public static final Set<String> thumbnailExtensions = new HashSet<>();

    static {
        videoExtensions.add("mp4");
        videoExtensions.add("avi");
        videoExtensions.add("mkv");
        videoExtensions.add("wmv");
        videoExtensions.add("webm");
        videoExtensions.add("mpeg");
        videoExtensions.add("flv");

        // vtt is the most supportable at html
//        subtitleExtensions.add("srt");
//        subtitleExtensions.add("sub");
        subtitleExtensions.add("vtt");
//        subtitleExtensions.add("smi");
//        subtitleExtensions.add("idx");
//        subtitleExtensions.add("ttml");

        thumbnailExtensions.add("jpeg");
        thumbnailExtensions.add("png");
        thumbnailExtensions.add("gif");
        thumbnailExtensions.add("webp");
        thumbnailExtensions.add("bmp");
        thumbnailExtensions.add("tiff");
        thumbnailExtensions.add("ico");
        thumbnailExtensions.add("svg");
    }
}
