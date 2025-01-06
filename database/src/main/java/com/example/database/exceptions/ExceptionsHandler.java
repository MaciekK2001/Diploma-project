package com.example.database.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<String> badRequestHandler(BadRequestException exception) {
        String message = exception.getMessage();

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<String> NotFoundHandler(NotFoundException exception) {
        String message = exception.getMessage();

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

}