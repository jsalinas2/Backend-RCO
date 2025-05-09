package com.develop.dental_api.model.dto;

import lombok.Data;

@Data
public class ClinicalRecordDTO {
    private String allergies;
    private String medicalHistory;
    private String familyHistory;
    private String treatmentPlan;
    private String evolutionNotes;
}