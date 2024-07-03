package ru.practicum.explorewithme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WrongTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleWrongTimeException(WrongTimeException ex) {
        return ex.getMessage();
    }
}