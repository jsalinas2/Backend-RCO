package com.develop.dental_api.model.mapper;

import com.develop.dental_api.model.dto.CreateServiceDTO;
import com.develop.dental_api.model.dto.ServiceDTO;
import com.develop.dental_api.model.dto.UpdateServiceDTO;
import com.develop.dental_api.model.entity.ServiceEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-26T08:27:37-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public ServiceDTO toDTO(ServiceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ServiceDTO serviceDTO = new ServiceDTO();

        serviceDTO.setServiceId( entity.getServiceId() );
        serviceDTO.setName( entity.getName() );
        serviceDTO.setDescription( entity.getDescription() );
        serviceDTO.setPrice( entity.getPrice() );
        serviceDTO.setIsRecurring( entity.getIsRecurring() );

        return serviceDTO;
    }

    @Override
    public ServiceEntity toEntity(CreateServiceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ServiceEntity serviceEntity = new ServiceEntity();

        serviceEntity.setName( dto.getName() );
        serviceEntity.setDescription( dto.getDescription() );
        serviceEntity.setPrice( dto.getPrice() );
        serviceEntity.setIsRecurring( dto.getIsRecurring() );

        return serviceEntity;
    }

    @Override
    public void updateEntityFromDto(UpdateServiceDTO dto, ServiceEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setName( dto.getName() );
        entity.setDescription( dto.getDescription() );
        entity.setPrice( dto.getPrice() );
        entity.setIsRecurring( dto.getIsRecurring() );
    }
}
