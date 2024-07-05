package com.vietcuong.simpleJwt.service.authentication;

import com.vietcuong.simpleJwt.response.AuthenticationResponse;
import com.vietcuong.simpleJwt.entity.authentication.Token;
import com.vietcuong.simpleJwt.entity.authentication.User;
import com.vietcuong.simpleJwt.repository.authentication.TokenRepository;
import com.vietcuong.simpleJwt.repository.authentication.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class AuthenticationService {

    // Repository for user data operations
    private final UserRepository userRepository;

    // Encoder for password encryption
    private final PasswordEncoder passwordEncoder;

    // Service for JWT token operations
    private final JwtService jwtService;

    // Manager for authentication processes
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    // Constructor to initialize dependencies
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager,
                                 TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    public AuthenticationResponse registerResponse(User userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());
        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        saveUserToken(token, user);
        String formattedDate = dateFormat.format(jwtService.extractExpirationTime(token));
        String formattedTime = timeFormat.format(jwtService.extractExpirationTime(token));

        return new AuthenticationResponse(token, "Registered successfully", formattedDate + " " + formattedTime);
    }

    public AuthenticationResponse authenticationResponse(User userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),
                userRequest.getPassword()));
        User user = userRepository.findByUsername(userRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        revokeAllTokenByUser(user);
        saveUserToken(token, user);
        String formattedDate = dateFormat.format(jwtService.extractExpirationTime(token));
        String formattedTime = timeFormat.format(jwtService.extractExpirationTime(token));
        return new AuthenticationResponse(token, "Logged in successfully", formattedDate + " " + formattedTime);
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokenListByUser = tokenRepository.findAllTokenByUser(user.getId());
        if (!validTokenListByUser.isEmpty()) {
            validTokenListByUser.forEach(t -> {
                t.setLoggedOut(true);
            });
        }
        tokenRepository.saveAll(validTokenListByUser);
    }

    private void saveUserToken(String token, User user) {
        // Create a new Token entity
        Token tokenEntity = new Token();
        // Set the token value received as parameter
        tokenEntity.setToken(token);
        // Set the loggedOut flag to false, indicating the token is active
        tokenEntity.setLoggedOut(false);
        // Associate the token entity with the user
        tokenEntity.setUser(user);
        // Save the token entity to the database using the injected tokenRepository
        tokenRepository.save(tokenEntity);
    }

}
