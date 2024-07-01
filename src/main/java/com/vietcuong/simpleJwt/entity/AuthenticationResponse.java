package com.vietcuong.simpleJwt.entity;

// Class representing the authentication response containing a token
public class AuthenticationResponse {

    private String token; // Private field to hold the authentication token

    // Constructor to initialize AuthenticationResponse with a token
    public AuthenticationResponse(String token) {
        this.token = token;
    }

    // Getter method to retrieve the authentication token
    public String getToken() {
        return token;
    }

    // Setter method to set the authentication token
    public void setToken(String token) {
        this.token = token;
    }
}
