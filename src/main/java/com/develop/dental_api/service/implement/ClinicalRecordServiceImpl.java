package com.develop.dental_api.service.implement;

import org.springframework.stereotype.Service;

import com.develop.dental_api.model.dto.ClinicalRecordCompleteDTO;
import com.develop.dental_api.model.dto.ClinicalRecordDTO;
import com.develop.dental_api.model.dto.ClinicalRecordRequestDTO;
import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.model.dto.UpdateClinicalRecordDTO;
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
    public ClinicalRecordCompleteDTO getRecordByUser(Integer userId) {
        ClinicalRecord record = clinicalRecordRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
        return clinicalRecordMapper.toCompleteDTO(record);
    }

    @Override
    public ClinicalRecordCompleteDTO getCompleteRecordByDni(String dni) {
        ClinicalRecord record = clinicalRecordRepository.findByProfileDni(dni)
                .orElseThrow(() -> new RuntimeException("Historial clínico no encontrado para el DNI: " + dni));
        
        return clinicalRecordMapper.toCompleteDTO(record);
    }

    @Override
    public MessageResponseDTO updateRecord(Integer clinicalId, UpdateClinicalRecordDTO dto) {
        ClinicalRecord record = clinicalRecordRepository.findById(clinicalId)
                .orElseThrow(() -> new RuntimeException("Historial clínico no encontrado"));
        
        // Actualizar solo los campos que no sean null
        if (dto.getAllergies() != null) {
            record.setAllergies(dto.getAllergies());
        }
        if (dto.getMedicalHistory() != null) {
            record.setMedicalHistory(dto.getMedicalHistory());
        }
        if (dto.getFamilyHistory() != null) {
            record.setFamilyHistory(dto.getFamilyHistory());
        }
        if (dto.getTreatmentPlan() != null) {
            record.setTreatmentPlan(dto.getTreatmentPlan());
        }
        if (dto.getEvolutionNotes() != null) {
            record.setEvolutionNotes(dto.getEvolutionNotes());
        }
        
        clinicalRecordRepository.save(record);
        return new MessageResponseDTO("Historial clínico actualizado correctamente");
    }
}

