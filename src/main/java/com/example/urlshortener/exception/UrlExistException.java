package com.example.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UrlExistException extends RuntimeException{

    public UrlExistException(String message) {
        super(message);
    }

}
