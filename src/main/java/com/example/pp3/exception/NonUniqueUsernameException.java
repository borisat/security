package com.example.pp3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NonUniqueUsernameException extends Exception {
    public NonUniqueUsernameException(String message) {
        super("Non unique username: " + message);
    }
}
