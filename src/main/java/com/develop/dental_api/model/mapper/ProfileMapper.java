package com.develop.dental_api.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.develop.dental_api.model.dto.UpdateProfileDTO;
import com.develop.dental_api.model.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromDto(UpdateProfileDTO dto, @MappingTarget Profile profile);
}