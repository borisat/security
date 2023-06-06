package com.example.pp3.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidTokenException extends Exception {

    public InvalidTokenException(String message) {
        super("Invalid token: " + message);
    }

}
