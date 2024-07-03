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

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    // Constructor to initialize dependencies
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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
        String formattedDate = dateFormat.format(jwtService.extractExpirationTime(token));
        String formattedTime = timeFormat.format(jwtService.extractExpirationTime(token));

        return new AuthenticationResponse(token, "Registered successfully", formattedDate + " " + formattedTime);
    }


    public AuthenticationResponse authenticationResponse(User userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),
                userRequest.getPassword()));
        User user = userRepository.findByUsername(userRequest.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        String formattedDate = dateFormat.format(jwtService.extractExpirationTime(token));
        String formattedTime = timeFormat.format(jwtService.extractExpirationTime(token));
        return new AuthenticationResponse(token, "Logged in successfully", formattedDate + " " + formattedTime);
    }
}
