package com.vietcuong.simpleJwt.filter;

import com.vietcuong.simpleJwt.service.JwtService;
import com.vietcuong.simpleJwt.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    // Override method to perform authentication logic for each incoming request
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Retrieve 'Authorization' header from HTTP request
        String authHeader = request.getHeader("Authorization");

        // Check if 'Authorization' header is absent or does not start with 'Bearer'
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            // If conditions are not met, continue with the filter chain
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from 'Authorization' header
        String token = authHeader.substring(7);

        // Extract username from JWT token using JwtService
        String username = jwtService.extractUsername(token);

        // Check if username is retrieved from the token and if user is not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load UserDetails from database using userDetailsService based on username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate JWT token using JwtService and UserDetails

            if (jwtService.isTokenValid(token, userDetails)) {
                // Create authentication token (UsernamePasswordAuthenticationToken) with UserDetails and
                // authorities
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                // Set authentication details from HTTP request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authenticated token in SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
