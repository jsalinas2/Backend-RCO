package com.develop.dental_api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.develop.dental_api.model.dto.RegisterUserRequestDTO;
import com.develop.dental_api.model.dto.UserLoginDTO;
import com.develop.dental_api.model.dto.UserProfileDTO;
import com.develop.dental_api.model.entity.Profile;
import com.develop.dental_api.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "clinicalRecord", ignore = true)
    @Mapping(target = "dentistAppointments", ignore = true)
    @Mapping(target = "patientAppointments", ignore = true)
    User toUserEntity(RegisterUserRequestDTO dto);

    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Profile toProfileEntity(RegisterUserRequestDTO dto, User user);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "firstName", source = "profile.firstName")
    @Mapping(target = "lastName", source = "profile.lastName")
    @Mapping(target = "phone", source = "profile.phone")
    @Mapping(target = "birthDate", source = "profile.birthDate")
    @Mapping(target = "dni", source = "profile.dni")
    @Mapping(target = "role", source = "user.role")
    UserProfileDTO toUserProfileDTO(User user, Profile profile);

    @Mapping(target = "id", source = "user.userId")
    UserLoginDTO toUserLoginDTO(User user);
}