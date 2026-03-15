package com.fintaxpro.user.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class PerformanceLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(PerformanceLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;

        // Retrieve request ID from the tracing filter
        String requestId = (String) request.getAttribute("REQUEST_ID");

        log.info("{} | {} {} completed in {} ms",
                requestId,
                request.getMethod(),
                request.getRequestURI(),
                duration
        );
    }
}
