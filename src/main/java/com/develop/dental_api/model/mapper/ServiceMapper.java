package com.develop.dental_api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.develop.dental_api.model.dto.ServiceDTO;
import com.develop.dental_api.model.dto.CreateServiceDTO;
import com.develop.dental_api.model.dto.UpdateServiceDTO;
import com.develop.dental_api.model.entity.ServiceEntity;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDTO toDTO(ServiceEntity entity);
    @Mapping(target = "serviceId", ignore = true)
    ServiceEntity toEntity(CreateServiceDTO dto);
    void updateEntityFromDto(UpdateServiceDTO dto, @MappingTarget ServiceEntity entity);
}
