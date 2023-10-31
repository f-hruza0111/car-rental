package com.hruza.carrental.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hruza.carrental.exception.AppException;
import com.hruza.carrental.exception.AppExceptionPayload;
import com.hruza.carrental.repository.TokenRepository;
import com.hruza.carrental.service.JwtService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Primary
@Order(2)
public class JWTFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final TokenRepository tokenRepository;
    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwt;
        String userEmail;

        if(header != null){
            String[] elements = header.split(" ");

            if(elements.length == 2 && "Bearer".equals(elements[0])){
                jwt = elements[1];

                try {
                    userEmail = jwtService.extractUsername(jwt);
                } catch (Exception e) {
                    throw new AppException("Token invalid", e);
                }


                if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                    boolean isPersistedTokenValid = tokenRepository.findByToken(jwt)
                            .map(t -> !t.getExpired() && !t.getRevoked()).orElse(false);

                    if(jwtService.isTokenValid(jwt, userDetails) && isPersistedTokenValid){
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }

            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);

    }
}
