package com.hruza.carrental.config.security;

import com.hruza.carrental.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        try {
            return http.
                    csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests( auth -> {
                        auth.requestMatchers("car-rental/*/registration").permitAll();
                        auth.requestMatchers("car-rental/customer/*").permitAll();
                        auth.requestMatchers("car-rental/rental-company/*").permitAll();
                        auth.anyRequest().authenticated();
                    })
                    .formLogin(Customizer.withDefaults())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Security config error");
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        try {
            return authenticationManagerBuilder
                    .authenticationProvider(daoAuthenticationProvider())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error building authentication manager");
        }
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
        provider.setUserDetailsService(appUserService);

        return provider;
    }
}
