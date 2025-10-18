package com.cib.stream.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

public class CspControlFilter extends RequestContextFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("CSP Filter...");

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ResponseConf responseWrapper = new ResponseConf(response);

        filterChain.doFilter(requestWrapper, responseWrapper);
        setCacheHeaders(responseWrapper, requestWrapper);

        responseWrapper.copyBodyToResponse();
    }

    private void setCacheHeaders(ContentCachingResponseWrapper responseWrapper, ContentCachingRequestWrapper requestWrapper) {
        responseWrapper.setHeader("Content-Security-Policy", "media-src cdn.ing.com; default-src 'self'");

        if (requestWrapper.getRequestURI().equals("/")) {
            StringBuilder csp = new StringBuilder();

            for (String header : responseWrapper.getHeaders("Content-Security-Policy")) {
                if (header.contains("media-src")) {
                    csp.append(header.replace("media-src", "media-src 'self' "));
                } else {
                    csp.append(header).append(";");
                }
            }

            responseWrapper.setHeader("Content-Security-Policy", csp.toString());
        }
    }

}
