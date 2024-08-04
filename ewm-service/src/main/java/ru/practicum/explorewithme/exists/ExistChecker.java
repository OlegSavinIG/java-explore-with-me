package ru.practicum.explorewithme.exists;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.AlreadyExistException;
import ru.practicum.explorewithme.exception.NotExistException;
import ru.practicum.explorewithme.user.repository.AdminUserRepository;
import ru.practicum.explorewithme.user.repository.RequestRepository;

/**
 * Component for checking the existence of various entities.
 */
@RequiredArgsConstructor
@Component
public class ExistChecker {
    /**
     * Component for checking the existence of various entities.
     */
    private final AdminUserRepository adminUserRepository;
    /**
     * Component for checking the existence of various entities.
     */
    private final EventRepository eventRepository;
    /**
     * Component for checking the existence of various entities.
     */
    private final CompilationRepository compilationRepository;
    /**
     * Component for checking the existence of various entities.
     */
    private final CategoryRepository categoryRepository;
    /**
     * Component for checking the existence of various entities.
     */
    private final RequestRepository requestRepository;

    /**
     * Checks if a user exists.
     *
     * @param userId the ID of the user
     * @throws NotExistException if the user does not exist
     */
    public void isUserExist(final Long userId) {
        boolean existsById = adminUserRepository.existsById(userId);
        if (!existsById) {
            throw new NotExistException("User not exists");
        }
    }

    /**
     * Checks if an event exists.
     *
     * @param eventId the ID of the event
     * @throws NotExistException if the event does not exist
     */
    public void isEventExists(final Long eventId) {
        boolean existsById = eventRepository.existsById(eventId);
        if (!existsById) {
            throw new NotExistException("Event not exists");
        }
    }

    /**
     * Checks if a compilation exists.
     *
     * @param compId the ID of the compilation
     * @throws NotExistException if the compilation does not exist
     */
    public void isCompilationExists(final Integer compId) {
        boolean existsById = compilationRepository.existsById(compId);
        if (!existsById) {
            throw new NotExistException("Compilation not exists");
        }
    }

    /**
     * Checks if a category exists.
     *
     * @param catId the ID of the category
     * @throws NotExistException if the category does not exist
     */
    public void isCategoryExists(final Integer catId) {
        boolean existsById = categoryRepository.existsById(catId);
        if (!existsById) {
            throw new NotExistException("Category not exists");
        }
    }

    /**
     * Checks if a request exists.
     *
     * @param reqId the ID of the request
     * @throws NotExistException if the request does not exist
     */
    public void isRequestExists(final Long reqId) {
        boolean existsById = requestRepository.existsById(reqId);
        if (!existsById) {
            throw new NotExistException("Request not exists");
        }
    }
    /**
     * Checks if a category exists.
     *
     * @param name the name of the category
     * @throws NotExistException if the category does not exist
     */
    public void isCategoryExistsByName(final String name) {
        boolean existsByName = categoryRepository.existsByName(name);
        if (existsByName) {
            throw new AlreadyExistException("Category already exist");
        }
    }
    /**
     * Checks if a user exists.
     *
     * @param email the email of the user
     * @throws NotExistException if the user does not exist
     */
    public void isUserExistsByEmail(final String email) {
        boolean existsByEmail = adminUserRepository.existsByEmail(email);
        if (existsByEmail) {
            throw new AlreadyExistException("Email already exists");
        }
    }
}
