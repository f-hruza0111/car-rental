package com.hruza.carrental.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hruza.carrental.entity.AppUser;
import com.hruza.carrental.entity.token.Token;
import com.hruza.carrental.exception.AppException;
import com.hruza.carrental.exception.AppExceptionPayload;
import com.hruza.carrental.repository.AppUserRepository;
import com.hruza.carrental.repository.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {


    private final TokenRepository tokenRepository;
    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {


        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwt;
        String userEmail;

        if(header != null){
            String[] elements = header.split(" ");

            if(elements.length == 2 && "Bearer".equals(elements[0])) {
                jwt = elements[1];

                try{
                    userEmail = jwtService.extractUsername(jwt);
                    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        AppUser user = appUserRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalStateException("User not found"));

                        invalidateTokens(user.getId());
                    }
                } catch (JwtException e){
                        AppExceptionPayload appExceptionPayload = new AppExceptionPayload(
                                e.getMessage(),
                                e.getCause(),
                                HttpStatus.UNAUTHORIZED,
                                LocalDateTime.now().toString());

                    try {
                        new ObjectMapper().writeValue(response.getOutputStream(), appExceptionPayload);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }


            }
        }
    }

    private void invalidateTokens(Long id) {
        List<Token> tokens = tokenRepository.findAllValidTokensByUser(id);
        tokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });

        tokenRepository.saveAll(tokens);

    }
}
