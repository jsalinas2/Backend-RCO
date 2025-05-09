package com.develop.dental_api.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develop.dental_api.model.dto.ClinicalRecordDTO;
import com.develop.dental_api.model.dto.ClinicalRecordRequestDTO;
import com.develop.dental_api.model.dto.MessageResponseDTO;
import com.develop.dental_api.service.ClinicalRecordService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "https://odontologiaweb.netlify.app")
@RestController
@RequestMapping("/api/clinical")
@RequiredArgsConstructor
public class ClinicalRecordController {

    private final ClinicalRecordService clinicalRecordService;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> create(@RequestBody ClinicalRecordRequestDTO request) {
        return ResponseEntity.ok(clinicalRecordService.createRecord(request));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<ClinicalRecordDTO> get(@PathVariable Integer user_id) {
        return ResponseEntity.ok(clinicalRecordService.getRecordByUser(user_id));
    }
}

