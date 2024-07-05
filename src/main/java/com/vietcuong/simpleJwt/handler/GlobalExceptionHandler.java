package com.vietcuong.simpleJwt.handler;

import com.vietcuong.simpleJwt.exception.ObjectNotValidException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<?> handleDuplicateUsernameException(){
        return ResponseEntity.badRequest().body("Username already exists");
    }

    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleException(ObjectNotValidException objectNotValidException){
        return ResponseEntity.badRequest().body(objectNotValidException.getErrorMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleDateFormatException(){
        return ResponseEntity.badRequest().body("Invalid date format. Expected format is yyyy-MM-dd");
    }

}
