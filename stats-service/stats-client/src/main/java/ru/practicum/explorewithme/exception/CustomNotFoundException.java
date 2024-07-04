package ru.practicum.explorewithme.exception;

/**
 * Custom exception for not found errors.
 */
public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(final String message) {
        super(message);
    }
}
