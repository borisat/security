package com.example.pp3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailValidationException extends Exception {
    public EmailValidationException(String message) {
        super("Incorrect email: " + message);
    }
}
