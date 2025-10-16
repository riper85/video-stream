package com.cib.stream.controller;

import com.cib.stream.service.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
class MovieControler {

    @Autowired
    private Movie movie;

    @GetMapping(value = "movie", produces = "video/mp4")
    InputStreamResource movie() throws FileNotFoundException {
        return movie.getStream();
    }
}
