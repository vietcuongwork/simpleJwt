package com.vietcuong.simpleJwt.handler;

import com.vietcuong.simpleJwt.Error;
import com.vietcuong.simpleJwt.response.RegistrationErrorResponse;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateUsernameException() {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Username already exists");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof DateTimeParseException) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid date format. Expected format is yyyy-MM-dd"));
        }

        // For other exceptions, or if root cause is null, handle generically
        String errorMessage = rootCause != null ? rootCause.getMessage() : "Invalid request format";
        return ResponseEntity.badRequest().body(Map.of("error", errorMessage.trim()));
    }

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException e) {
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
        return sortedErrors;
    }*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RegistrationErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        RegistrationErrorResponse response = new RegistrationErrorResponse();
        if (!e.getBindingResult().getAllErrors().isEmpty()) {
            String fieldName = e.getBindingResult().getFieldError().getField();
            String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
            response.setDetail(errorMessage);
            switch (fieldName) {
                case "fullName":
                    response.setStatusCode(Error.ClientRegisterValidationError.INVALID_FULL_NAME.getCode());
                    response.setMessage(Error.ClientRegisterValidationError.INVALID_FULL_NAME.getMessage());
                    break;
                case "username":
                    response.setStatusCode(Error.ClientRegisterValidationError.INVALID_USERNAME.getCode());
                    response.setMessage(Error.ClientRegisterValidationError.INVALID_USERNAME.getMessage());
                    break;
                case "email":
                    response.setStatusCode(Error.ClientRegisterValidationError.INVALID_EMAIL.getCode());
                    response.setMessage(Error.ClientRegisterValidationError.INVALID_EMAIL.getMessage());
                    break;
                default:
                    response.setStatusCode(Error.ClientRegisterValidationError.INVALID_DATE_OF_BIRTH.getCode());
                    response.setMessage(Error.ClientRegisterValidationError.INVALID_DATE_OF_BIRTH.getMessage());
                    break;
            }
        }

        // Return the single error response
        return response;
    }
}

/*    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleException(
            ObjectNotValidException objectNotValidException) {
        return ResponseEntity.badRequest()
                .body(objectNotValidException.getErrorMessage());
    }*/