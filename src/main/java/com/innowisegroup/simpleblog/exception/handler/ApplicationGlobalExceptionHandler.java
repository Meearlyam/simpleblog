package com.innowisegroup.simpleblog.exception.handler;

import com.innowisegroup.simpleblog.dto.ResponseWithExceptionDto;
import com.innowisegroup.simpleblog.exception.UserLastnameValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ApplicationGlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserLastnameValidationException.class)
    public ResponseWithExceptionDto handleUserLastnameValidationException(UserLastnameValidationException ex) {
        return new ResponseWithExceptionDto(
                ex.getMessage(),
                new Date());
    }
}
