package ru.practicum.explorewithme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class ExploreWithMeApp {

    private ExploreWithMeApp() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void main(final String[] args) {
        SpringApplication.run(ExploreWithMeApp.class, args);
    }
}
