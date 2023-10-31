package com.hruza.carrental.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class RequestFailureHandler implements AuthenticationFailureHandler, AccessDeniedHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        handleInternal(request, response, exception);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
       handleInternal(request, response, accessDeniedException);
    }

    private void handleInternal(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put(
                "timestamp",
                Calendar.getInstance().getTime());
        data.put(
                "exception",
                e.getMessage());

        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }
}
