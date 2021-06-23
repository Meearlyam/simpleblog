package com.innowisegroup.simpleblog.exception.handler;

import com.innowisegroup.simpleblog.dto.ResponseWithExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationGlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseWithExceptionDto handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> violations = new HashMap<>();
        ex.getConstraintViolations()
                .forEach(error -> {
                    String name = error.getPropertyPath().toString();
                    String message = error.getMessage();
                    violations.put(name, message);
                });
        return new ResponseWithExceptionDto(
                "Domain entity is not valid",
                violations,
                new Date());
    }
}
