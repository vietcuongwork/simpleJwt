package com.vietcuong.simpleJwt.response;

import lombok.Data;

import java.util.Map;

@Data
public class RegistrationErrorResponse {
    private String statusCode;
    private String message;
    private String detail;
}
