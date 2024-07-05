package com.vietcuong.simpleJwt.config;

import com.vietcuong.simpleJwt.entity.authentication.Token;
import com.vietcuong.simpleJwt.repository.authentication.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    public CustomLogoutHandler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Extract the Authorization header from the request
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is missing or doesn't start with "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return; // Exit if the token format is incorrect
        }

        // Extract the JWT token from the Authorization header
        String token = authHeader.substring(7);

        // Retrieve the stored token from the database based on the extracted token value
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        // Invalidate the token (log out the user) if it exists in the database
        if (storedToken != null) {
            // Set the loggedOut flag to true
            storedToken.setLoggedOut(true);
            // Save the updated token entity back to the database
            tokenRepository.save(storedToken);
        }
    }
}
