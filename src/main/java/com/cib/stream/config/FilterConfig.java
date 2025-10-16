package com.cib.stream.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

//@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<Filter> cspControlFilter() {
        CspControlFilter cspControlFilter = new CspControlFilter("cdn.ing.com");

        return createFilterRegistration(cspControlFilter, "/*");
    }

    @Bean
    public FilterRegistrationBean<Filter> cspControlFilter2() {
        CspControlFilter cspControlFilter = new CspControlFilter("'self'");

        return createFilterRegistration(cspControlFilter, "/movie");
    }

    private FilterRegistrationBean<Filter> createFilterRegistration(Filter filter, String pattern) {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(filter);

        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add(pattern);

        filterRegistrationBean.setUrlPatterns(urlPatterns);

        return filterRegistrationBean;
    }
}
