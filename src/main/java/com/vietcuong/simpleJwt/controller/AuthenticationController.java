package com.vietcuong.simpleJwt.controller;

import com.vietcuong.simpleJwt.entity.AuthenticationResponse;
import com.vietcuong.simpleJwt.entity.User;
import com.vietcuong.simpleJwt.service.AuthenticationService;
import com.vietcuong.simpleJwt.service.JwtService;
import com.vietcuong.simpleJwt.service.UserDetailsServiceImpl;
import com.vietcuong.simpleJwt.service.UserService;
import org.springframework.http.HttpStatus;
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

    private final UserDetailsServiceImpl userDetailsService;

    // Constructor to initialize AuthenticationService and UserService dependencies
    public AuthenticationController(AuthenticationService authenticationService, UserService userService,
                                    UserDetailsServiceImpl userDetailsService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    // Endpoint to handle user registration
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User userRequest) {
        // Delegate registration logic to AuthenticationService and return the response
        if (userDetailsService.existsByUsername(userRequest.getUsername())) {
            return new ResponseEntity<String>("Username has already been used", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(authenticationService.registerResponse(userRequest));
    }

    // Endpoint to handle user login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userRequest) {
        // Delegate login logic to AuthenticationService and return the response
        if (userDetailsService.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity.ok(authenticationService.authenticationResponse(userRequest));
        }
        return new ResponseEntity<String>("Incorrect username", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> deleteUser(@RequestBody User userRequest) {
        // Delegate delete user logic to UserService based on user ID
        if(!userDetailsService.existsByUsername(userRequest.getUsername())){
            return new ResponseEntity<String>("User doesn't exist", HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(userRequest.getId());
        return ResponseEntity.ok("User deleted successfully");
    }
}
