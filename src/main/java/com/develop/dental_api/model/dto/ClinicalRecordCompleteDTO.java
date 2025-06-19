package com.develop.dental_api.model.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClinicalRecordCompleteDTO {
    private Integer clinicalId;
    private UserProfileDTO user;
    private String allergies;
    private String medicalHistory;
    private String familyHistory;
    private String treatmentPlan;
    private String evolutionNotes;
    private LocalDateTime createdAt;
    private List<TreatmentDoneDTO> treatmentsDone;
}