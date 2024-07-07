package ru.practicum.explorewithme.user.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.user.model.UserEntity;
import ru.practicum.explorewithme.user.model.UserRequest;
import ru.practicum.explorewithme.user.model.UserResponse;
import ru.practicum.explorewithme.user.model.mapper.UserMapper;
import ru.practicum.explorewithme.user.repository.AdminUserRepository;
import ru.practicum.explorewithme.user.service.admin.AdminUserService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserRepository repository;
    @Override
    public UserResponse addNewUser(UserRequest userRequest) {
        UserEntity savedEntity = repository.save(UserMapper.toEntity(userRequest));
        return UserMapper.toResponse(savedEntity);
    }

    @Override
    public Collection<UserResponse> findByIds(List<Long> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from/size, size);
        Page<UserEntity> userEntities = repository.findByIdIn(ids, pageable);
        return userEntities.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<UserResponse> findAll(int from, int size) {
        Pageable pageable = PageRequest.of(from/size, size);
        Page<UserEntity> userEntities = repository.findAll(pageable);
        return userEntities.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(Long userId) {
        repository.deleteById(userId);
    }
}
