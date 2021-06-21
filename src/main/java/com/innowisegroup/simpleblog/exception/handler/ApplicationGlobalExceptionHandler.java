package com.innowisegroup.simpleblog.exception.handler;

import com.innowisegroup.simpleblog.dto.ResponseWithExceptionDto;
import com.innowisegroup.simpleblog.exception.UserValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ApplicationGlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserValidationException.class)
    public ResponseWithExceptionDto handleUserLastnameValidationException(UserValidationException ex) {
        return new ResponseWithExceptionDto(
                ex.getClass().getName(),
                ex.getCause().getMessage(),
                new Date());
    }
}
