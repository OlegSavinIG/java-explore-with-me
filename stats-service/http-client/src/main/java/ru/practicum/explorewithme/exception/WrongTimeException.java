package ru.practicum.explorewithme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongTimeException extends RuntimeException {
    public WrongTimeException(String s) {
        super(s);
    }
}
