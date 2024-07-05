package com.vietcuong.simpleJwt.validator;

import com.vietcuong.simpleJwt.entity.Client;
import com.vietcuong.simpleJwt.exception.ObjectNotValidException;
import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectValidator<T> {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public void validate(T objectToValidate) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
        if (!violations.isEmpty()) {
            var errorMessage = violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new ObjectNotValidException(errorMessage);
        }

    }
}
