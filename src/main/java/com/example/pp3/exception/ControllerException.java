package com.example.pp3.exception;

import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ControllerException extends Exception {
    public ControllerException(String message, Exception e) {
        super("Incorrect input data: " + message);
    }


}