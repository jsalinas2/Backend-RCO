package com.develop.dental_api.model.mapper;

import com.develop.dental_api.model.dto.ClinicalRecordCompleteDTO;
import com.develop.dental_api.model.dto.ClinicalRecordDTO;
import com.develop.dental_api.model.dto.ClinicalRecordRequestDTO;
import com.develop.dental_api.model.dto.TreatmentDoneDTO;
import com.develop.dental_api.model.dto.UserProfileDTO;
import com.develop.dental_api.model.entity.ClinicalRecord;
import com.develop.dental_api.model.entity.Profile;
import com.develop.dental_api.model.entity.Service;
import com.develop.dental_api.model.entity.TreatmentDone;
import com.develop.dental_api.model.entity.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-19T13:42:59-0500",
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

    @Override
    public ClinicalRecordCompleteDTO toCompleteDTO(ClinicalRecord entity) {
        if ( entity == null ) {
            return null;
        }

        ClinicalRecordCompleteDTO.ClinicalRecordCompleteDTOBuilder clinicalRecordCompleteDTO = ClinicalRecordCompleteDTO.builder();

        clinicalRecordCompleteDTO.user( userToUserProfileDTO( entity.getUser() ) );
        clinicalRecordCompleteDTO.treatmentsDone( treatmentDoneListToTreatmentDoneDTOList( entity.getTreatmentsDone() ) );
        clinicalRecordCompleteDTO.clinicalId( entity.getClinicalId() );
        clinicalRecordCompleteDTO.allergies( entity.getAllergies() );
        clinicalRecordCompleteDTO.medicalHistory( entity.getMedicalHistory() );
        clinicalRecordCompleteDTO.familyHistory( entity.getFamilyHistory() );
        clinicalRecordCompleteDTO.treatmentPlan( entity.getTreatmentPlan() );
        clinicalRecordCompleteDTO.evolutionNotes( entity.getEvolutionNotes() );
        clinicalRecordCompleteDTO.createdAt( entity.getCreatedAt() );

        return clinicalRecordCompleteDTO.build();
    }

    @Override
    public TreatmentDoneDTO toTreatmentDoneDTO(TreatmentDone treatmentDone) {
        if ( treatmentDone == null ) {
            return null;
        }

        TreatmentDoneDTO.TreatmentDoneDTOBuilder treatmentDoneDTO = TreatmentDoneDTO.builder();

        treatmentDoneDTO.serviceName( treatmentDoneServiceName( treatmentDone ) );
        treatmentDoneDTO.treatmentsId( treatmentDone.getTreatmentsId() );
        treatmentDoneDTO.treatmentDate( treatmentDone.getTreatmentDate() );
        treatmentDoneDTO.observations( treatmentDone.getObservations() );
        treatmentDoneDTO.status( treatmentDone.getStatus() );
        treatmentDoneDTO.createdAt( treatmentDone.getCreatedAt() );

        return treatmentDoneDTO.build();
    }

    private String userProfileFirstName(User user) {
        Profile profile = user.getProfile();
        if ( profile == null ) {
            return null;
        }
        return profile.getFirstName();
    }

    private String userProfileLastName(User user) {
        Profile profile = user.getProfile();
        if ( profile == null ) {
            return null;
        }
        return profile.getLastName();
    }

    private String userProfilePhone(User user) {
        Profile profile = user.getProfile();
        if ( profile == null ) {
            return null;
        }
        return profile.getPhone();
    }

    private LocalDate userProfileBirthDate(User user) {
        Profile profile = user.getProfile();
        if ( profile == null ) {
            return null;
        }
        return profile.getBirthDate();
    }

    private String userProfileDni(User user) {
        Profile profile = user.getProfile();
        if ( profile == null ) {
            return null;
        }
        return profile.getDni();
    }

    protected UserProfileDTO userToUserProfileDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserProfileDTO userProfileDTO = new UserProfileDTO();

        userProfileDTO.setUserId( user.getUserId() );
        userProfileDTO.setFirstName( userProfileFirstName( user ) );
        userProfileDTO.setLastName( userProfileLastName( user ) );
        userProfileDTO.setPhone( userProfilePhone( user ) );
        userProfileDTO.setBirthDate( userProfileBirthDate( user ) );
        userProfileDTO.setDni( userProfileDni( user ) );
        if ( user.getRole() != null ) {
            userProfileDTO.setRole( user.getRole().name() );
        }
        userProfileDTO.setEmail( user.getEmail() );

        return userProfileDTO;
    }

    protected List<TreatmentDoneDTO> treatmentDoneListToTreatmentDoneDTOList(List<TreatmentDone> list) {
        if ( list == null ) {
            return null;
        }

        List<TreatmentDoneDTO> list1 = new ArrayList<TreatmentDoneDTO>( list.size() );
        for ( TreatmentDone treatmentDone : list ) {
            list1.add( toTreatmentDoneDTO( treatmentDone ) );
        }

        return list1;
    }

    private String treatmentDoneServiceName(TreatmentDone treatmentDone) {
        Service service = treatmentDone.getService();
        if ( service == null ) {
            return null;
        }
        return service.getName();
    }
}
