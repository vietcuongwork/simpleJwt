package com.vietcuong.simpleJwt.controller;

import com.vietcuong.simpleJwt.entity.AuthenticationResponse;
import com.vietcuong.simpleJwt.entity.User;
import com.vietcuong.simpleJwt.service.AuthenticationService;
import com.vietcuong.simpleJwt.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class AuthenticationController {

    // Service for handling authentication operations
    private final AuthenticationService authenticationService;

    // Service for handling user-related operations
    private final UserService userService;

    // Constructor to initialize AuthenticationService and UserService dependencies
    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    // Endpoint to handle user registration
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User userRequest) {
        // Delegate registration logic to AuthenticationService and return the response
        return ResponseEntity.ok(authenticationService.registerResponse(userRequest));
    }

    // Endpoint to handle user login
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User userRequest) {
        // Delegate login logic to AuthenticationService and return the response
        return ResponseEntity.ok(authenticationService.authenticationResponse(userRequest));
    }

    // Endpoint to retrieve all users as a list of strings
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<String>> getAllUser() {
        // Retrieve all users from UserService and convert to a list of strings
        List<String> userList = userService.allUsersToString();
        return ResponseEntity.ok(userList);
    }

    // Endpoint to delete a user by ID (admin privilege required)
    @DeleteMapping("/admin/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestBody User userRequest) {
        // Delegate delete user logic to UserService based on user ID
        userService.deleteUser(userRequest.getId());
        return ResponseEntity.noContent().build(); // Return 204 No Content on successful deletion
    }
}
