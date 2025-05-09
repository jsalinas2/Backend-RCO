package com.develop.dental_api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.develop.dental_api.model.dto.ClinicalRecordDTO;
import com.develop.dental_api.model.dto.ClinicalRecordRequestDTO;
import com.develop.dental_api.model.entity.ClinicalRecord;
import com.develop.dental_api.model.entity.User;

@Mapper(componentModel = "spring")
public interface ClinicalRecordMapper {

    @Mapping(target = "user", source = "userId")
    ClinicalRecord toEntity(ClinicalRecordRequestDTO dto);

    default User map(Integer userId) {
        if (userId == null) return null;
        User user = new User();
        user.setUserId(userId);
        return user;
    }

    ClinicalRecordDTO toDTO(ClinicalRecord entity);
}