package ru.practicum.explorewithme.config;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import ru.practicum.explorewithme.exception.CustomBadRequestException;
import ru.practicum.explorewithme.exception.CustomGenericException;
import ru.practicum.explorewithme.exception.CustomNotFoundException;

import java.io.IOException;

/**
 * Custom error handler for REST responses.
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(
            final ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(
            final ClientHttpResponse response) throws IOException {
        System.out.println("Error status code: " + response.getStatusCode());
        System.out.println("Error status text: " + response.getStatusText());

        switch (response.getStatusCode()) {
            case BAD_REQUEST:
                throw new CustomBadRequestException(
                        "Bad request: " + response.getStatusText());
            case NOT_FOUND:
                throw new CustomNotFoundException(
                        "Not found: " + response.getStatusText());
            default:
                throw new CustomGenericException(
                        "Generic error: " + response.getStatusText());
        }
    }
}
