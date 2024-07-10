package com.vietcuong.simpleJwt.handler;

import com.vietcuong.simpleJwt.Error;
import com.vietcuong.simpleJwt.entity.Client;
import com.vietcuong.simpleJwt.response.RegistrationErrorResponse;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PSQLException.class)
    public RegistrationErrorResponse handleDuplicateUsernameException() {
        RegistrationErrorResponse response = new RegistrationErrorResponse();
        response.setStatusCode(Error.GlobalError.CLIENT_REGISTRATION_ERROR.getCode());
        response.setDescription(Error.GlobalError.CLIENT_REGISTRATION_ERROR.getDescription());
        Map<String, String> error = new HashMap<>();
        error.put("username", "Username already exists");
        response.setDetail(error);
        return response;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RegistrationErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        RegistrationErrorResponse response = new RegistrationErrorResponse();
        response.setStatusCode(Error.GlobalError.CLIENT_REGISTRATION_ERROR.getCode());
        response.setDescription(Error.GlobalError.CLIENT_REGISTRATION_ERROR.getDescription());
        Throwable rootCause = ex.getRootCause();
        Map<String, String> error = new HashMap<>();
        if (rootCause instanceof DateTimeParseException) {
            error.put("dateOfBirth", "Invalid date format. Expected format is yyyy-MM-dd");
        }
        // For other exceptions, or if root cause is null, handle generically
        else {
            error.put("error", "Invalid request format");
        }
        response.setDetail(error);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RegistrationErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        RegistrationErrorResponse response = new RegistrationErrorResponse();
        response.setStatusCode(Error.GlobalError.CLIENT_REGISTRATION_ERROR.getCode());
        response.setDescription(Error.GlobalError.CLIENT_REGISTRATION_ERROR.getDescription());
        Map<String, String> errors = new HashMap<>();
        // Iterate through each validation error
        e.getBindingResult().getAllErrors().forEach((error) -> {
            // Extract field name that caused the error
            String fieldName = ((FieldError) error).getField();
            // Extract error message
            String errorMessage = error.getDefaultMessage();
            // Populate errors map with field name and error message
            errors.put(fieldName, errorMessage);
        });
        // Get the field order from the Client class using reflection
        List<String> fieldOrder = new ArrayList<>();
        for (Field field : Client.class.getDeclaredFields()) {
            fieldOrder.add(field.getName());
        }

        // Sort errors based on field order
        Map<String, String> sortedErrors = new LinkedHashMap<>();
        for (String field : fieldOrder) {
            if (errors.containsKey(field)) {
                sortedErrors.put(field, errors.get(field));
            }
        }
        // Print all errors to the console for debugging
        errors.forEach((field, error) -> System.out.println("Field: " + field + ", Error: " + error));
        // Return map containing field names and error messages
        response.setDetail(sortedErrors);
        return response;
    }

}

/*    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleException(
            ObjectNotValidException objectNotValidException) {
        return ResponseEntity.badRequest()
                .body(objectNotValidException.getErrorMessage());
    }*/