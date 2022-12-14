package com.fasttrackit.budgetapplication.controller;


import com.fasttrackit.budgetapplication.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)

    public ErrorResponse handleRuntimeException(ResourceNotFoundException exception) {
        return new ErrorResponse(exception.getMessage() + exception.getEntityId());
    }
}

record ErrorResponse(String message) {

}
