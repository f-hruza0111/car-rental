package com.hruza.carrental.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hruza.carrental.entity.AppUser;
import com.hruza.carrental.entity.token.Token;
import com.hruza.carrental.entity.token.TokenType;
import com.hruza.carrental.http.communication.AuthenticationRequest;
import com.hruza.carrental.http.communication.AuthenticationResponse;
import com.hruza.carrental.repository.AppUserRepository;
import com.hruza.carrental.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        AppUser user = repository.findByEmail(request.email()).orElseThrow(() -> new UsernameNotFoundException("User " + request.email() + " not found"));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Token accessTokenEntity = Token.builder()
                .token(accessToken)
                .tokenType(TokenType.ACCESS)
                .user(user)
                .expired(false)
                .revoked(false)
                .build();

        Token refreshTokenEntity = Token.builder()
                .token(refreshToken)
                .tokenType(TokenType.REFRESH)
                .user(user)
                .expired(false)
                .revoked(false)
                .build();

        revokeAllUserTokens(user.getId());

        tokenRepository.save(accessTokenEntity);
        tokenRepository.save(refreshTokenEntity);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userID(user.getId())
                .role(user.getRole())
                .build();
    }

    public void revokeAllUserTokens(Long userID){
        List<Token> tokens = tokenRepository.findAllValidTokensByUser(userID);
        invalidateTokens(tokens);
    }



    public void revokeAllAccessTokens(Long userID){
        List<Token> tokens = tokenRepository.findAllValidAccessTokensByUser(userID);
        invalidateTokens(tokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken;
        String userEmail;

        if(header != null){
            String[] elements = header.split(" ");

            if(elements.length == 2 && "Bearer".equals(elements[0])){
                refreshToken = elements[1];
                userEmail = jwtService.extractUsername(refreshToken);

                if(userEmail != null){
                    AppUser userDetails = this.repository.findByEmail(userEmail).orElseThrow(()-> new IllegalStateException("User not found"));

                    if(jwtService.isTokenValid(refreshToken, userDetails)){

                      String accessToken = jwtService.generateToken(userDetails);
                      revokeAllAccessTokens(userDetails.getId());

                      Token token = Token.builder()
                              .token(accessToken)
                              .expired(false)
                              .revoked(false)
                              .user(userDetails)
                              .tokenType(TokenType.ACCESS)
                              .build();

                      tokenRepository.save(token);


                      AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                              .accessToken(accessToken)
                              .refreshToken(refreshToken)
//                              .userID(userDetails.getId())
//                              .role(userDetails.getRole())
                              .build();

                      new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
                    }
                }
            }
        }
    }

    private void invalidateTokens(List<Token> tokens) {
        tokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });

        tokenRepository.saveAll(tokens);
    }
}
