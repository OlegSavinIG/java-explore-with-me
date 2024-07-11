package ru.practicum.explorewithme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for ExploreWithMe.
 */
@SpringBootApplication(scanBasePackages = "ru.practicum.explorewithme")
public class ExploreWithMeApp {
    /**
     * The main method which serves as the entry point for the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMeApp.class, args);
    }
}
