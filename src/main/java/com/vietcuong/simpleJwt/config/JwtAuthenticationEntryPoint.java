package com.vietcuong.simpleJwt.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.ErrorManager;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final ErrorManager errorManager = new ErrorManager();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        errorManager.error("Unauthorized error: " + authException.getMessage(), authException,
                ErrorManager.GENERIC_FAILURE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Invalid or expired token\"}");

    }
}
