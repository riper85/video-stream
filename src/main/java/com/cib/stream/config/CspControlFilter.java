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

    private String cspControl;

    CspControlFilter(String cspControl) {
        this.cspControl = cspControl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("CSP Filter...");

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());
        setCacheHeaders(responseWrapper, requestWrapper);

        responseWrapper.copyBodyToResponse();
    }

    private void setCacheHeaders(ContentCachingResponseWrapper responseWrapper, ContentCachingRequestWrapper requestWrapper) {
        responseWrapper.setHeader("Content-Security-Policy", "default-src 'self'");
        responseWrapper.setHeader("Content-Security-Policy", "media-src cdn.ing.com");

        if (requestWrapper.getRequestURI().equals("/")) {
            StringBuilder csp = new StringBuilder("media-src 'self';");

            for (String header : responseWrapper.getHeaders("Content-Security-Policy")) {
                csp.append(header).append(";");
            }

            responseWrapper.setHeader("Content-Security-Policy", csp.toString());
        }
    }

}
