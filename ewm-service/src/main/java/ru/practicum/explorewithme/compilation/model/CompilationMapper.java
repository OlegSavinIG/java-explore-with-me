package ru.practicum.explorewithme.compilation.model;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.explorewithme.event.model.EventResponse;
import ru.practicum.explorewithme.event.service.EventService;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompilationMapper {

    @Mapping(target = "events", expression = "java(mapIdsToEvents(request.getEvents(), eventService))")
    CompilationEntity toEntity(CompilationRequest request, @Context EventService service);

    CompilationResponse toResponse(CompilationEntity entity);

    default List<EventResponse> mapIdsToEvents(List<Long> ids, @Context EventService eventService) {
        return eventService.getEventsByIds(ids);
    }
}
