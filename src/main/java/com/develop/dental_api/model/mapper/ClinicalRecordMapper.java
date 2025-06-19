package com.develop.dental_api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.develop.dental_api.model.dto.ClinicalRecordCompleteDTO;
import com.develop.dental_api.model.dto.ClinicalRecordDTO;
import com.develop.dental_api.model.dto.ClinicalRecordRequestDTO;
import com.develop.dental_api.model.dto.TreatmentDoneDTO;
import com.develop.dental_api.model.entity.ClinicalRecord;
import com.develop.dental_api.model.entity.User;
import com.develop.dental_api.model.entity.TreatmentDone;

@Mapper(componentModel = "spring")
public interface ClinicalRecordMapper {

    @Mapping(target = "user", source = "userId")
    ClinicalRecord toEntity(ClinicalRecordRequestDTO dto);
    
    ClinicalRecordDTO toDTO(ClinicalRecord entity);
    
    @Mapping(target = "user.userId", source = "user.userId")
    @Mapping(target = "user.firstName", source = "user.profile.firstName")
    @Mapping(target = "user.lastName", source = "user.profile.lastName")
    @Mapping(target = "user.phone", source = "user.profile.phone")
    @Mapping(target = "user.birthDate", source = "user.profile.birthDate")
    @Mapping(target = "user.dni", source = "user.profile.dni")
    @Mapping(target = "user.role", source = "user.role")
    @Mapping(target = "treatmentsDone", source = "treatmentsDone")
    ClinicalRecordCompleteDTO toCompleteDTO(ClinicalRecord entity);
    
    @Mapping(target = "serviceName", source = "service.name")
    TreatmentDoneDTO toTreatmentDoneDTO(TreatmentDone treatmentDone);
    
    default User map(Integer userId) {
        if (userId == null) return null;
        User user = new User();
        user.setUserId(userId);
        return user;
    }
}