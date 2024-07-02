package com.vietcuong.simpleJwt.entity;

import lombok.Getter;
import lombok.Setter;

// Class representing the authentication response containing a token
@Setter
@Getter
public class AuthenticationResponse {

    // Setter method to set the authentication token
    // Getter method to retrieve the authentication token
    private String token; // Private field to hold the authentication token
    private String message;

    // Constructor to initialize AuthenticationResponse with a token
    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public AuthenticationResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }


}
