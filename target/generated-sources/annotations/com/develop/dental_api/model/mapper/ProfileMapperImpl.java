package com.develop.dental_api.model.mapper;

import com.develop.dental_api.model.dto.UpdateProfileDTO;
import com.develop.dental_api.model.entity.Profile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-26T08:27:37-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public void updateProfileFromDto(UpdateProfileDTO dto, Profile profile) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getFirstName() != null ) {
            profile.setFirstName( dto.getFirstName() );
        }
        if ( dto.getLastName() != null ) {
            profile.setLastName( dto.getLastName() );
        }
        if ( dto.getPhone() != null ) {
            profile.setPhone( dto.getPhone() );
        }
    }
}
