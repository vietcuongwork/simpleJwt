package com.vietcuong.simpleJwt.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AuthenticationErrorResponse {
    private String statusCode;
    private String description;
    private Map<String, String> detail;
    //    private ResponseEntity<?> responseContent;

    public Map<String, String> authenticationResponseMapping(AuthenticationResponse response) {
        Map<String, String> authenticationResponse = new HashMap<>();
        authenticationResponse.put("token", response.getToken());
        authenticationResponse.put("message", response.getMessage());
        authenticationResponse.put("expirationTime", response.getExpirationTime());
        return authenticationResponse;
    }

}
