package com.develop.dental_api.model.mapper;

import com.develop.dental_api.model.dto.RegisterUserRequestDTO;
import com.develop.dental_api.model.dto.UserLoginDTO;
import com.develop.dental_api.model.dto.UserProfileDTO;
import com.develop.dental_api.model.entity.Profile;
import com.develop.dental_api.model.entity.User;
import com.develop.dental_api.model.enums.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-26T05:07:01-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUserEntity(RegisterUserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );
        if ( dto.getRole() != null ) {
            user.setRole( Enum.valueOf( Role.class, dto.getRole() ) );
        }

        user.setCreatedAt( java.time.LocalDateTime.now() );

        return user;
    }

    @Override
    public Profile toProfileEntity(RegisterUserRequestDTO dto, User user) {
        if ( dto == null && user == null ) {
            return null;
        }

        Profile profile = new Profile();

        if ( dto != null ) {
            profile.setFirstName( dto.getFirstName() );
            profile.setLastName( dto.getLastName() );
            profile.setPhone( dto.getPhone() );
            profile.setBirthDate( dto.getBirthDate() );
            profile.setDni( dto.getDni() );
        }
        profile.setUser( user );
        profile.setCreatedAt( java.time.LocalDateTime.now() );

        return profile;
    }

    @Override
    public UserProfileDTO toUserProfileDTO(User user, Profile profile) {
        if ( user == null && profile == null ) {
            return null;
        }

        UserProfileDTO userProfileDTO = new UserProfileDTO();

        if ( user != null ) {
            userProfileDTO.setUserId( user.getUserId() );
            userProfileDTO.setEmail( user.getEmail() );
            if ( user.getRole() != null ) {
                userProfileDTO.setRole( user.getRole().name() );
            }
        }
        if ( profile != null ) {
            userProfileDTO.setFirstName( profile.getFirstName() );
            userProfileDTO.setLastName( profile.getLastName() );
            userProfileDTO.setPhone( profile.getPhone() );
            userProfileDTO.setBirthDate( profile.getBirthDate() );
            userProfileDTO.setDni( profile.getDni() );
        }

        return userProfileDTO;
    }

    @Override
    public UserLoginDTO toUserLoginDTO(User user) {
        if ( user == null ) {
            return null;
        }

        Integer id = null;
        String email = null;
        String role = null;

        id = user.getUserId();
        email = user.getEmail();
        if ( user.getRole() != null ) {
            role = user.getRole().name();
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO( id, email, role );

        return userLoginDTO;
    }
}
