package com.develop.dental_api.model.mapper;

import com.develop.dental_api.model.dto.AvailabilityDTO;
import com.develop.dental_api.model.dto.CreateAvailabilityDTO;
import com.develop.dental_api.model.entity.Availability;
import com.develop.dental_api.model.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-26T05:07:01-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class AvailabilityMapperImpl implements AvailabilityMapper {

    @Override
    public AvailabilityDTO toDTO(Availability entity) {
        if ( entity == null ) {
            return null;
        }

        AvailabilityDTO availabilityDTO = new AvailabilityDTO();

        availabilityDTO.setDentistId( entityDentistUserId( entity ) );
        availabilityDTO.setId( entity.getId() );
        availabilityDTO.setDayOfWeek( entity.getDayOfWeek() );
        availabilityDTO.setStartTime( entity.getStartTime() );
        availabilityDTO.setEndTime( entity.getEndTime() );

        return availabilityDTO;
    }

    @Override
    public Availability toEntity(CreateAvailabilityDTO dto, User dentist) {
        if ( dto == null && dentist == null ) {
            return null;
        }

        Availability availability = new Availability();

        if ( dto != null ) {
            availability.setDayOfWeek( dto.getDayOfWeek() );
            availability.setStartTime( dto.getStartTime() );
            availability.setEndTime( dto.getEndTime() );
        }
        availability.setDentist( dentist );

        return availability;
    }

    private Integer entityDentistUserId(Availability availability) {
        User dentist = availability.getDentist();
        if ( dentist == null ) {
            return null;
        }
        return dentist.getUserId();
    }
}
