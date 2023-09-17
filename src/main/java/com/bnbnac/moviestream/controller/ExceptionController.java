package com.bnbnac.moviestream.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        String message = e.getMessage();
        if (message == null) {
            message = "An exception occurred.";
        }
        model.addAttribute("message", message);
        return "exception";
    }
}