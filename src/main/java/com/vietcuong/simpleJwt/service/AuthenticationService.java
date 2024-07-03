package com.vietcuong.simpleJwt.service;

import com.vietcuong.simpleJwt.entity.AuthenticationResponse;
import com.vietcuong.simpleJwt.entity.User;
import com.vietcuong.simpleJwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

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

    // Constructor to initialize dependencies
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // Method to handle registration of a new user and generate an authentication token
    public AuthenticationResponse registerResponse(User userRequest) {
        // Create a new User entity and set its properties based on the request
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());

        // Save the user entity to the repository
        user = userRepository.save(user);

        // Generate a JWT token for the registered user
        String token = jwtService.generateToken(user);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(jwtService.extractExpirationTime(token));
        // Return an AuthenticationResponse containing the generated token
        return new AuthenticationResponse(token, "Registered successfully", formattedTime);
    }

    // Method to handle user authentication and generate an authentication token
    public AuthenticationResponse authenticationResponse(User userRequest) {
        // Authenticate the user with the provided username and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),
                userRequest.getPassword()));

        // Retrieve the user details from the repository
        User user = userRepository.findByUsername(userRequest.getUsername()).orElseThrow();

        // Generate a JWT token for the authenticated user
        String token = jwtService.generateToken(user);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(jwtService.extractExpirationTime(token));
        // Return an AuthenticationResponse containing the generated token
        return new AuthenticationResponse(token, "Logged in", formattedTime);
    }
}
