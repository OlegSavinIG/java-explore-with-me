package ru.practicum.explorewithme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for EWM Statistic Client.
 */
@SpringBootApplication
public class EWMStatisticClientApp {
    /**
     * The main method which serves as the entry point for the application.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(EWMStatisticClientApp.class, args);
    }
}
