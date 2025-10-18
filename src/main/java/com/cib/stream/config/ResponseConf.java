package com.cib.stream.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class ResponseConf extends ContentCachingResponseWrapper {
    public ResponseConf(HttpServletResponse response) {
        super(response);
    }

    public void setHeader(String name, String value) {
        System.out.println(name);
        System.out.println(value);
        if (name.equals("Content-Security-Policy")) {
            value = value.replace("media-src", "media-src 'self' ");
        }
        super.setHeader(name, value);
    }
}
