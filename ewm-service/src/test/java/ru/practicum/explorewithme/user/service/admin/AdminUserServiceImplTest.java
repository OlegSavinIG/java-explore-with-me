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
import ru.practicum.explorewithme.exception.NotExistException;
import ru.practicum.explorewithme.user.model.UserEntity;
import ru.practicum.explorewithme.user.model.UserRequest;
import ru.practicum.explorewithme.user.model.UserResponse;
import ru.practicum.explorewithme.user.model.mapper.UserMapper;
import ru.practicum.explorewithme.user.repository.AdminUserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminUserServiceImplTest {

    @Mock
    private AdminUserRepository repository;

    @InjectMocks
    private AdminUserServiceImpl service;

    private UserRequest userRequest;
    private UserEntity userEntity;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userRequest = UserRequest.builder()
                .name("Test User")
                .email("test@example.com")
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .build();

        userResponse = UserResponse.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .build();
    }

    @Test
    void testAddNewUser() {
        when(repository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserResponse response = service.addNewUser(userRequest);

        verify(repository, times(1)).save(any(UserEntity.class));
        assert response.getId().equals(userEntity.getId());
    }

    @Test
    void testFindByIds() {
        Page<UserEntity> page = new PageImpl<>(Collections.singletonList(userEntity));
        when(repository.findByIdIn(anyList(), any(PageRequest.class))).thenReturn(page);

        List<Long> ids = Collections.singletonList(1L);
        Collection<UserResponse> responses = service.findByIds(ids, 0, 10);

        verify(repository, times(1)).findByIdIn(anyList(), any(PageRequest.class));
        assert responses.size() == 1;
        assert responses.iterator().next().getId().equals(userEntity.getId());
    }

    @Test
    void testFindAll() {
        Page<UserEntity> page = new PageImpl<>(Collections.singletonList(userEntity));
        when(repository.findAll(any(PageRequest.class))).thenReturn(page);

        Collection<UserResponse> responses = service.findAll(0, 10);

        verify(repository, times(1)).findAll(any(PageRequest.class));
        assert responses.size() == 1;
        assert responses.iterator().next().getId().equals(userEntity.getId());
    }

    @Test
    void testDeleteUserById() {
        when(repository.existsById(anyLong())).thenReturn(true);

        service.deleteUserById(1L);

        verify(repository, times(1)).existsById(anyLong());
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteUserById_NotExist() {
        when(repository.existsById(anyLong())).thenReturn(false);

        try {
            service.deleteUserById(1L);
        } catch (NotExistException e) {
            assert e.getMessage().equals("User not exist");
        }

        verify(repository, times(1)).existsById(anyLong());
        verify(repository, times(0)).deleteById(anyLong());
    }

    @Test
    void testFindById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(userEntity));

        UserResponse response = service.findById(1L);

        verify(repository, times(1)).findById(anyLong());
        assert response.getId().equals(userEntity.getId());
    }

    @Test
    void testFindById_NotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            service.findById(1L);
        } catch (NotExistException e) {
            assert e.getMessage().equals("User does not exist");
        }

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void testFindUserEntity() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(userEntity));

        UserEntity response = service.findUserEntity(1L);

        verify(repository, times(1)).findById(anyLong());
        assert response.getId().equals(userEntity.getId());
    }

    @Test
    void testFindUserEntity_NotExist() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        try {
            service.findUserEntity(1L);
        } catch (NotExistException e) {
            assert e.getMessage().equals("User does not exist");
        }

        verify(repository, times(1)).findById(anyLong());
    }
}
