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
import java.util.UUID;

@Component
public class RequestTracingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestTracingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Generate unique request ID
        String requestId = "REQ-" + UUID.randomUUID().toString().substring(0, 8);

        // Attach to request for downstream logs
        request.setAttribute("REQUEST_ID", requestId);

        // Add to response header (optional but useful)
        response.setHeader("X-Request-ID", requestId);

        log.info("{} | Incoming request: {} {}", requestId, request.getMethod(), request.getRequestURI());

        filterChain.doFilter(request, response);

        log.info("{} | Completed request: {} {}", requestId, request.getMethod(), request.getRequestURI());
    }
}

