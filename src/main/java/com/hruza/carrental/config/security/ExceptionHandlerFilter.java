package com.hruza.carrental.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hruza.carrental.exception.AppException;
import com.hruza.carrental.exception.AppExceptionPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Order(1)
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AppException e) {

            HttpStatus badRequest = HttpStatus.FORBIDDEN;
            AppExceptionPayload payload = new AppExceptionPayload(
                    e.getMessage(),
                    e.getCause(),
                    badRequest,
                    LocalDateTime.now().toString()
            );

            response.setStatus(badRequest.value());
            new ObjectMapper().writeValue(response.getOutputStream(), payload);
    }
}


}