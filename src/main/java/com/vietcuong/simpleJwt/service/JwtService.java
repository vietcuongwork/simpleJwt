package com.vietcuong.simpleJwt.service;

import com.vietcuong.simpleJwt.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

// Service class for JWT operations
@Service
public class JwtService {

    // Secret key used for JWT signing and verification
    private final String SECRET_KEY = "bb89cd30d2a1a8deb78d86d9e442046e9d829df36633379ac855753a52eeed2a";

    // Default constructor
    public JwtService() {
    }

    // Method to get the SecretKey instance from the secret key string
    private SecretKey getSecretKey() {
        byte[] keyBytes = (byte[]) Decoders.BASE64URL.decode(
                "bb89cd30d2a1a8deb78d86d9e442046e9d829df36633379ac855753a52eeed2a");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Method to generate a JWT token for a given user
    public String generateToken(User user) {
        return Jwts.builder().subject(user.getUsername()).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis() + 1000 * 60)) // Token valid for 24 hours
                .signWith(this.getSecretKey()).compact();
    }

    // Method to extract all claims from a JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(this.getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    // Method to extract a specific claim from a JWT token using a resolver function
    private <T> T extractSpecificClaim(String token, Function<Claims, T> resolver) {
        Claims claims = this.extractAllClaims(token);
        return resolver.apply(claims);
    }

    // Method to extract the username from a JWT token
    public String extractUsername(String token) {
        return this.extractSpecificClaim(token, Claims::getSubject);
    }

    // Method to extract the expiration time from a JWT token
    private Date extractExpirationTime(String token) {
        return this.extractSpecificClaim(token, Claims::getExpiration);
    }

    // Method to check if a JWT token is expired
    private boolean isTokenExpired(String token) {
        return this.extractExpirationTime(token).before(new Date());
    }

    // Method to validate if a JWT token is valid for a given user details
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = this.extractUsername(token);
        // Compare the extracted username with the username from UserDetails and check if token is not expired
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }

}
