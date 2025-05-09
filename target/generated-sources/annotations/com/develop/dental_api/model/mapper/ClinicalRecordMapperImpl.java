package com.develop.dental_api.model.mapper;

import com.develop.dental_api.model.dto.ClinicalRecordDTO;
import com.develop.dental_api.model.dto.ClinicalRecordRequestDTO;
import com.develop.dental_api.model.entity.ClinicalRecord;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T20:35:08-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class ClinicalRecordMapperImpl implements ClinicalRecordMapper {

    @Override
    public ClinicalRecord toEntity(ClinicalRecordRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ClinicalRecord clinicalRecord = new ClinicalRecord();

        clinicalRecord.setUser( map( dto.getUserId() ) );
        clinicalRecord.setAllergies( dto.getAllergies() );
        clinicalRecord.setMedicalHistory( dto.getMedicalHistory() );
        clinicalRecord.setFamilyHistory( dto.getFamilyHistory() );
        clinicalRecord.setTreatmentPlan( dto.getTreatmentPlan() );
        clinicalRecord.setEvolutionNotes( dto.getEvolutionNotes() );

        return clinicalRecord;
    }

    @Override
    public ClinicalRecordDTO toDTO(ClinicalRecord entity) {
        if ( entity == null ) {
            return null;
        }

        ClinicalRecordDTO clinicalRecordDTO = new ClinicalRecordDTO();

        clinicalRecordDTO.setAllergies( entity.getAllergies() );
        clinicalRecordDTO.setMedicalHistory( entity.getMedicalHistory() );
        clinicalRecordDTO.setFamilyHistory( entity.getFamilyHistory() );
        clinicalRecordDTO.setTreatmentPlan( entity.getTreatmentPlan() );
        clinicalRecordDTO.setEvolutionNotes( entity.getEvolutionNotes() );

        return clinicalRecordDTO;
    }
}
