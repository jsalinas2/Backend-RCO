package com.develop.dental_api.service;

import com.develop.dental_api.model.dto.ClinicalRecordDTO;
import com.develop.dental_api.model.dto.ClinicalRecordRequestDTO;
import com.develop.dental_api.model.dto.MessageResponseDTO;

public interface ClinicalRecordService {

    MessageResponseDTO createRecord(ClinicalRecordRequestDTO dto);

    ClinicalRecordDTO getRecordByUser(Integer userId);
}