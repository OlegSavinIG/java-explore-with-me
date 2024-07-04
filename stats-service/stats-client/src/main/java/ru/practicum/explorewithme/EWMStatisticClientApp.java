package ru.practicum.explorewithme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for EWM Statistic Client.
 */
@SpringBootApplication
public final class EWMStatisticClientApp {
    private EWMStatisticClientApp() {
        throw new UnsupportedOperationException("This is a utility "
                + "class and cannot be instantiated");
    }

    /**
     * The main method which serves as the entry point for the application.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(EWMStatisticClientApp.class, args);
    }
}
