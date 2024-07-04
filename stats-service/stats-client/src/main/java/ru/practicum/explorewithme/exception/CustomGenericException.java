package ru.practicum.explorewithme.exception;

/**
 * Custom exception for generic errors.
 */
public class CustomGenericException extends RuntimeException {
    public CustomGenericException(final String message) {
        super(message);
    }
}
