package com.vietcuong.simpleJwt.config;

import com.vietcuong.simpleJwt.filter.JwtAuthenticationFilter;
import com.vietcuong.simpleJwt.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Instance of UserDetailsServiceImpl to provide user details service
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    // Instance of JwtAuthenticationFilter for JWT token processing
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    // Constructor to initialize UserDetailsServiceImpl and JwtAuthenticationFilter instances
    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;

    }

    // Bean definition for SecurityFilterChain to configure security settings
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disable CSRF protection
                .authorizeHttpRequests(request -> request.requestMatchers("/login/**", "/register/**").permitAll() //
                        // Permit access to login and register endpoints without authentication
                        .requestMatchers("/user/**").hasAnyAuthority("ADMIN", "USER") // Require ADMIN or USER
                        // authority for endpoints under /user/**
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") // Require ADMIN authority for endpoints
                        // under /admin/**
                        .anyRequest().authenticated()) // Require authentication for all other requests
                .userDetailsService(userDetailsServiceImpl) // Set UserDetailsServiceImpl as the user details service
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //
                // Configure session management to be stateless
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT
                // authentication filter before UsernamePasswordAuthenticationFilter
                .build();
    }

    // Bean definition for PasswordEncoder to encode passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCryptPasswordEncoder for password encoding
    }

    // Bean definition for AuthenticationManager to manage authentication processes
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Retrieve AuthenticationManager from
        // AuthenticationConfiguration
    }
}
