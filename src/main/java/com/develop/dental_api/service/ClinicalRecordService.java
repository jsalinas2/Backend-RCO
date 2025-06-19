package com.develop.dental_api.service;

import com.develop.dental_api.model.dto.ClinicalRecordCompleteDTO;
import com.develop.dental_api.model.dto.ClinicalRecordDTO;
import com.develop.dental_api.model.dto.ClinicalRecordRequestDTO;
import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.model.dto.UpdateClinicalRecordDTO;

public interface ClinicalRecordService {
    MessageResponseDTO createRecord(ClinicalRecordRequestDTO dto);
    ClinicalRecordCompleteDTO getRecordByUser(Integer userId);
    ClinicalRecordCompleteDTO getCompleteRecordByDni(String dni);  // Nuevo método
    MessageResponseDTO updateRecord(Integer clinicalId, UpdateClinicalRecordDTO dto);
}
