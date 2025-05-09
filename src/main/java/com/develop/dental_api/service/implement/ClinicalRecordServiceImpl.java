package com.develop.dental_api.service.implement;

import org.springframework.stereotype.Service;

import com.develop.dental_api.model.dto.ClinicalRecordDTO;
import com.develop.dental_api.model.dto.ClinicalRecordRequestDTO;
import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.model.entity.ClinicalRecord;
import com.develop.dental_api.model.mapper.ClinicalRecordMapper;
import com.develop.dental_api.repository.ClinicalRecordRepository;
import com.develop.dental_api.service.ClinicalRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClinicalRecordServiceImpl implements ClinicalRecordService {

    private final ClinicalRecordRepository clinicalRecordRepository;
    private final ClinicalRecordMapper clinicalRecordMapper;

    @Override
    public MessageResponseDTO createRecord(ClinicalRecordRequestDTO dto) {
        ClinicalRecord record = clinicalRecordMapper.toEntity(dto);
        clinicalRecordRepository.save(record);
        return new MessageResponseDTO("Historial clínico creado");
    }

    @Override
    public ClinicalRecordDTO getRecordByUser(Integer userId) {
        ClinicalRecord record = clinicalRecordRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        return clinicalRecordMapper.toDTO(record);
    }
}

