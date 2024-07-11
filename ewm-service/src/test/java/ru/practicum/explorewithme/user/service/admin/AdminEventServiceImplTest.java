package ru.practicum.explorewithme.user.service.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.explorewithme.category.model.CategoryEntity;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.event.model.EventEntity;
import ru.practicum.explorewithme.event.model.EventRequest;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.model.EventStatus;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.NotExistException;
import ru.practicum.explorewithme.user.model.EventSearchCriteriaForAdmin;
import ru.practicum.explorewithme.user.model.UserEntity;
import ru.practicum.explorewithme.user.model.mapper.UserMapper;
import ru.practicum.explorewithme.user.repository.AdminEventRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminEventServiceImplTest {

    @Mock
    private AdminEventRepository repository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private AdminEventServiceImpl service;

    private EventRequest eventRequest;
    private EventEntity eventEntity;
    private EventResponse eventResponse;
    private EventSearchCriteriaForAdmin criteria;

    @BeforeEach
    void setUp() {
        eventRequest = EventRequest.builder()
                .annotation("Test Annotation")
                .description("Test Description")
                .title("Test Title")
                .eventDate(LocalDateTime.now().plusDays(1))
                .paid(true)
                .participantLimit(100)
                .requestModeration(true)
                .category(1)
                .stateAction("PUBLISH_EVENT")
                .build();

        eventEntity = EventEntity.builder()
                .id(1L)
                .annotation("Test Annotation")
                .description("Test Description")
                .title("Test Title")
                .eventDate(LocalDateTime.now().plusDays(1))
                .paid(true)
                .participantLimit(100)
                .requestModeration(true)
                .state("PENDING")
                .build();

        eventResponse = EventResponse.builder()
                .id(1L)
                .annotation("Test Annotation")
                .description("Test Description")
                .title("Test Title")
                .eventDate(LocalDateTime.now().plusDays(1))
                .paid(true)
                .participantLimit(100)
                .requestModeration(true)
                .state(EventStatus.PUBLISHED)
                .build();

        criteria = new EventSearchCriteriaForAdmin();
        criteria.setUsers(Collections.singletonList(1L));
        criteria.setStates(Collections.singletonList("PENDING"));
        criteria.setCategories(Collections.singletonList(1));
        criteria.setRangeStart(LocalDateTime.now().minusDays(1));
        criteria.setRangeEnd(LocalDateTime.now().plusDays(1));
    }

    @Test
    void testGetEvents() {
        Page<EventEntity> page = new PageImpl<>(Collections.singletonList(eventEntity));
        when(repository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(page);

        List<EventResponse> responses = service.getEvents(criteria, 0, 10);

        verify(repository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
        assert responses.size() == 1;
        assert responses.get(0).getId().equals(eventEntity.getId());
    }

    @Test
    void testApproveEvent() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(eventEntity));
        when(eventRepository.save(any(EventEntity.class))).thenReturn(eventEntity);
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(new CategoryEntity()));

        EventResponse response = service.approveEvent(eventRequest, 1L);

        verify(eventRepository, times(1)).findById(anyLong());
        verify(eventRepository, times(1)).save(any(EventEntity.class));
        assert response != null;
        assert response.getId().equals(eventEntity.getId());
    }

    @Test
    void testApproveEvent_NotExist() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            service.approveEvent(eventRequest, 1L);
        } catch (NotExistException e) {
            assert e.getMessage().equals("Event with id=1 was not found");
        }

        verify(eventRepository, times(1)).findById(anyLong());
        verify(eventRepository, times(0)).save(any(EventEntity.class));
    }

    @Test
    void testApproveEvent_InvalidCategory() {
        when(eventRepository.findById(anyLong())).thenReturn(Optional.of(eventEntity));
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        try {
            service.approveEvent(eventRequest, 1L);
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("Category not found");
        }

        verify(eventRepository, times(1)).findById(anyLong());
        verify(categoryRepository, times(1)).findById(anyInt());
        verify(eventRepository, times(0)).save(any(EventEntity.class));
    }
}
