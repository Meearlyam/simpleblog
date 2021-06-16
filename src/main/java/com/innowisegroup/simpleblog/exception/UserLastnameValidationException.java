package com.innowisegroup.simpleblog.exception;

public class UserLastnameValidationException extends Exception {

    public UserLastnameValidationException() {
        super();
    }

    public UserLastnameValidationException(String message) {
        super(message);
    }

    public UserLastnameValidationException(Throwable cause) {
        super(cause);
    }

    public UserLastnameValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
