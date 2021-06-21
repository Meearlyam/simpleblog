package com.innowisegroup.simpleblog.exception;

public class UserValidationException extends RuntimeException {

    public UserValidationException() {
        super();
    }

    public UserValidationException(String message) {
        super(message);
    }

    public UserValidationException(Throwable cause) {
        super(cause);
    }

    public UserValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
