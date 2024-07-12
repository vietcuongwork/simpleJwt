package com.vietcuong.simpleJwtAndValidation.response;

import lombok.Data;

import java.util.Map;

@Data
public class RegistrationErrorResponse {
    private String statusCode;
    private String description;
    private Map<String, String> detail;
}
