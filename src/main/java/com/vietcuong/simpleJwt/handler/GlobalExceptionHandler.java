package com.vietcuong.simpleJwt.handler;

import com.vietcuong.simpleJwt.exception.ObjectNotValidException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<?> handleException(){
        return ResponseEntity.badRequest().body("Username already exists");
    }

    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleException(ObjectNotValidException objectNotValidException){
        return ResponseEntity.badRequest().body(objectNotValidException.getErrorMessage());
    }
}
