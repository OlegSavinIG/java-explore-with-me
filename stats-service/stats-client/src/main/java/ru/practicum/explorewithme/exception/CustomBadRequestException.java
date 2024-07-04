package ru.practicum.explorewithme.exception;

/**
 * Custom exception for bad requests.
 */
public class CustomBadRequestException extends RuntimeException {
    public CustomBadRequestException(final String message) {
        super(message);
    }
}
