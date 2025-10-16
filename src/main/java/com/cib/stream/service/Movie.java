package com.cib.stream.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.springframework.http.MediaType.ALL;

@Component
public class Movie {

    public InputStreamResource getStream() throws FileNotFoundException {

        File file = new File("/Users/cib/Dev/Workplace/stream/src/main/resources/static/welcome.mp4");
        InputStream fis = new FileInputStream(file);

        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(ALL);
        headers.set("Accept-Ranges", "bytes");
        headers.set("Expires", "0");
        headers.set("Cache-Control", "no-cache, no-store");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Transfer-Encoding", "binary");

        return new ResponseEntity<>(new InputStreamResource(fis), headers, HttpStatus.PARTIAL_CONTENT).getBody();
    }
}
