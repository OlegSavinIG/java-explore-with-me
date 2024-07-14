package ru.practicum.explorewithme.subscription.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.explorewithme.subscription.model.SubscriptionEntity;
import ru.practicum.explorewithme.subscription.model.SubscriptionResponse;
import ru.practicum.explorewithme.user.model.UserEntity;
import ru.practicum.explorewithme.user.model.UserResponse;
import ru.practicum.explorewithme.user.model.mapper.UserMapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SubscriptionMapper {
    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    @Mapping(target = "subscriber", source = "subscriber", qualifiedByName = "toUserResponse")
    @Mapping(target = "user", source = "user", qualifiedByName = "toUserResponse")
    SubscriptionResponse toResponse(SubscriptionEntity entity);

    default UserResponse toUserResponse(UserEntity entity) {
        return UserMapper.toResponse(entity);
    }
}
