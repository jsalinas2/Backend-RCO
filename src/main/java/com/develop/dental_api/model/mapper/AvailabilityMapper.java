package com.develop.dental_api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.develop.dental_api.model.dto.AvailabilityDTO;
import com.develop.dental_api.model.dto.CreateAvailabilityDTO;
import com.develop.dental_api.model.entity.Availability;
import com.develop.dental_api.model.entity.User;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {
    @Mapping(target = "dentistId", source = "dentist.userId")
    AvailabilityDTO toDTO(Availability entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dentist", source = "dentist")
    Availability toEntity(CreateAvailabilityDTO dto, User dentist);
}
