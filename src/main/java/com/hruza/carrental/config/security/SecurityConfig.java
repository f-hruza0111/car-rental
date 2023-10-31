package com.hruza.carrental.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final LogoutHandler logoutHandler;
    private final JWTFilter jwtAuthFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        try {
            return http
                    .logout(logout -> {
                        logout.logoutUrl("/car-rental/logout");
                        logout.addLogoutHandler(logoutHandler);
                        logout.logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()));

                    })
                    .addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider())
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests( auth -> {
                        auth.requestMatchers("car-rental/company/details/**").permitAll();
                        auth.requestMatchers("car-rental/ads/**").permitAll();
                        auth.requestMatchers("car-rental/login").permitAll();
                        auth.requestMatchers("car-rental/refresh-token").permitAll();
                        auth.requestMatchers("car-rental/*/registration").permitAll();
                        auth.requestMatchers("car-rental/customer/**").authenticated();
                        auth.requestMatchers("car-rental/rental-company/**").authenticated();
//                        auth.requestMatchers("car-rental/customer/**").permitAll();
//                        auth.requestMatchers("car-rental/rental-company/**").permitAll();
                        auth.anyRequest().denyAll();
                    })




                    .cors(Customizer.withDefaults())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Security config error");
        }
    }

    @Bean
    public RequestFailureHandler requestFailureHandler() {
        return new RequestFailureHandler();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Primary
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
        provider.setUserDetailsService(appUserService);

        return provider;
    }


}
